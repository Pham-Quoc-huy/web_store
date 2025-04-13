package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.Cart;

import java.util.Optional;

public interface ICartService {
    Cart createCartForUser(Long userId);
    Optional<Cart> getCartByUserId(Long userId);
}
