package com.kiva.application.service;

import org.springframework.stereotype.Service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Service
public interface PaypalService {
	Payment createPayment(Long total, String currency, String method, String intent, String description,
			String cancelURL, String successURL) throws PayPalRESTException;

	Payment Executepayment(String paymentId, String payerId) throws PayPalRESTException;
}
