package com.kiva.application.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kiva.application.service.PaypalService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Component
public class PaypalServiceImpl implements PaypalService {

	@Autowired
	private APIContext apiContext;

	@Override
	public Payment createPayment(Long total, String currency, String method, String intent, String description,
			String cancelURL, String successURL) throws PayPalRESTException {
		Amount amount = new Amount();
		amount.setCurrency(currency);
		//total = new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		double tmp = (double)300000 / 23000;
		tmp = new BigDecimal(tmp).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		amount.setTotal(String.format("%.3f", tmp));
		
		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		
		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());
		
		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelURL);
		redirectUrls.setReturnUrl(successURL);
		payment.setRedirectUrls(redirectUrls);
		
		return payment.create(apiContext);
	}

	@Override
	public Payment Executepayment(String paymentId, String payerId) throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(payerId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
}
