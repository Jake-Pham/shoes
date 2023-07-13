package com.kiva.application.service;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public interface VNPayService {
	String createOrder(long total, String urlReturn);
	int orderReturn(HttpServletRequest request);
}
