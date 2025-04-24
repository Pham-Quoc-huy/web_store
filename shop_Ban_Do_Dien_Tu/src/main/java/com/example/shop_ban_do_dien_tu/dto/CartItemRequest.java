package com.example.shop_ban_do_dien_tu.dto;

import lombok.Data;

@Data


public class CartItemRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
