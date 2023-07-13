package com.kiva.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private long id;

    private long totalPrice;

    private long productPrice;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private int status;

    private String statusText;

    private int sizeVn;

    private double sizeUs;

    private double sizeCm;

    private String productName;

    private String productImg;
    
	private int payment;
	  
	private String paymentText;
	
	private String shipping;
	
	private long discount;

    public OrderDetailDTO (long id, long totalPrice, long productPrice, String receiverName, String receiverPhone, String receiverAddress,int status, int sizeVn, String productName, String productImg, int payment, String shipping) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.productPrice = productPrice;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAddress = receiverAddress;
        this.status = status;
        this.sizeVn = sizeVn;
        this.productName = productName;
        this.productImg = productImg;
        this.payment = payment;
        this.shipping = shipping;
    }
}
