package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.CartItem;

import java.util.List;

public interface ICartItemService {
    List<CartItem> getItemsByCart(Long cartId);
    CartItem addOrUpdateItem(Long cartId, Long productId, int quantity);
    void removeItem(Long cartItemId);
    void updateQuantity(Long itemId, int quantity);
}
