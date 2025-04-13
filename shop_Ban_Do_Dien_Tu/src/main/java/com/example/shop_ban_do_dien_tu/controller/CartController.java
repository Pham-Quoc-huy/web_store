package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Cart;
import com.example.shop_ban_do_dien_tu.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    // ✅ Lấy giỏ hàng theo user
    @GetMapping("/{userId}")
    public String viewCart(@PathVariable Long userId, Model model) {
        Cart cart = cartService.getCartByUserId(userId)
                .orElseGet(() -> cartService.createCartForUser(userId));
        model.addAttribute("cart", cart);
        return "cart-view"; // Tên view HTML
    }
}
