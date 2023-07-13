package com.kiva.application.controller.shop;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.kiva.application.entity.Order;
import com.kiva.application.model.request.CreateOrderRequest;
import com.kiva.application.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {

	@Autowired
	PaypalService paypalService;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@PostMapping("/api/paypal")
	public String payment(@ModelAttribute("order") Order order) {
		try {
			Payment payment = paypalService.createPayment(order.getTotalPrice(), "USD", "Paypal", "Sale", order.getNote(),
					"http://localhost:8080" + CANCEL_URL, "http://localhost:8080" + SUCCESS_URL);
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){
					return "redirect:" + links.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@GetMapping(CANCEL_URL)
	public String cancelPay() {
		return "shop/cancel";
	}

	@GetMapping(SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String PaymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = paypalService.Executepayment(PaymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				return "shop/success";
			}
		} catch (PayPalRESTException e) {
			// log.error(e.getMessage())
			System.out.println(e.getMessage());
		}
		return "shop/success";
	}
}
