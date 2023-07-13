package com.kiva.application.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatusDTO implements Serializable {
	private String status;
	private String message;
	private String data;
}
