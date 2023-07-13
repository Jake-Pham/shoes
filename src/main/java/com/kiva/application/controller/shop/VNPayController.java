package com.kiva.application.controller.shop;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kiva.application.model.dto.PaymentResDTO;
import com.kiva.application.service.OrderService;
import com.kiva.application.service.VNPayService;

@Controller
public class VNPayController {
	
	@Autowired
	private VNPayService vnPayService;
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/api/payment/create_payment")
	public ResponseEntity<?> createPayment(@RequestParam long orderTotal,  HttpServletRequest request){	
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, baseUrl);
        PaymentResDTO 	paymentRestDTO = new PaymentResDTO();
        paymentRestDTO.setStatus("Ok");
        paymentRestDTO.setMessage("Successfully");
        paymentRestDTO.setURL(vnpayUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentRestDTO);
	}
	
	@GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);       
        return paymentStatus == 1 ? "shop/ordersuccess" : "shop/orderfail";
    }
}
