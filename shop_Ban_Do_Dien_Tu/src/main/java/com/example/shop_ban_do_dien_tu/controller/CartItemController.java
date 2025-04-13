package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Cart;
import com.example.shop_ban_do_dien_tu.model.User;
import com.example.shop_ban_do_dien_tu.service.ICartItemService;
import com.example.shop_ban_do_dien_tu.service.ICartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart-items")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;

    public CartItemController(ICartItemService itemService, ICartService cartService) {
        this.cartItemService = itemService;
        this.cartService = cartService;
    }

    // ✅ Xem giỏ hàng theo cartId
    @GetMapping("/cart/{cartId}")
    public String viewCartItems(@PathVariable Long cartId, Model model) {
        model.addAttribute("items", cartItemService.getItemsByCart(cartId));
        model.addAttribute("cartId", cartId);
        return "cart-items"; // tên view Thymeleaf
    }

    // ✅ Thêm sản phẩm vào giỏ (dùng session để lấy user)
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return "redirect:/login";
        }

        Cart cart = cartService.getCartByUserId(user.getId())
                .orElseGet(() -> cartService.createCartForUser(user.getId()));

        cartItemService.addOrUpdateItem(cart.getId(), productId, quantity);
        redirectAttributes.addFlashAttribute("success", "Đã thêm vào giỏ hàng.");
        return "redirect:/products/" + productId;
    }

    // ✅ Xoá sản phẩm khỏi giỏ
    @GetMapping("/delete/{itemId}")
    public String removeFromCart(@PathVariable Long itemId, @RequestParam Long cartId) {
        cartItemService.removeItem(itemId);
        return "redirect:/cart-items/cart/" + cartId;
    }
}