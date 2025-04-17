package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Cart;
import com.example.shop_ban_do_dien_tu.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController  // Chuyển từ @Controller sang @RestController
@RequestMapping("/cart")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    // ✅ Lấy giỏ hàng theo user (trả về JSON)
    @GetMapping("/{userId}")
    public Cart viewCart(@PathVariable Long userId) {
        // Trả về Cart đối tượng (Spring sẽ tự chuyển đổi thành JSON)
        return cartService.getCartByUserId(userId)
                .orElseGet(() -> cartService.createCartForUser(userId));
    }
}

