package com.example.shop_ban_do_dien_tu.dto;

import lombok.Data;

@Data
public class CheckoutRequest {
    private Long userId;
    private String paymentMethod;
    private String shippingAddress;
}
