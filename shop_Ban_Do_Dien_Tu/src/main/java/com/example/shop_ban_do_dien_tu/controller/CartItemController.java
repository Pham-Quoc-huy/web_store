package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Cart;
import com.example.shop_ban_do_dien_tu.model.CartItem;
import com.example.shop_ban_do_dien_tu.model.User;
import com.example.shop_ban_do_dien_tu.service.ICartItemService;
import com.example.shop_ban_do_dien_tu.service.ICartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController  // Chuyển từ @Controller sang @RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;

    public CartItemController(ICartItemService itemService, ICartService cartService) {
        this.cartItemService = itemService;
        this.cartService = cartService;
    }

    // ✅ Xem giỏ hàng theo cartId (trả về JSON)
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> viewCartItems(@PathVariable Long cartId) {
        List<CartItem> items = cartItemService.getItemsByCart(cartId);
        return ResponseEntity.ok(items); // Trả về danh sách CartItem dưới dạng JSON
    }

    // ✅ Thêm sản phẩm vào giỏ (dùng session để lấy user, trả về JSON)
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Long productId,
                                            @RequestParam(defaultValue = "1") int quantity,
                                            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng.");
        }

        Cart cart = cartService.getCartByUserId(user.getId())
                .orElseGet(() -> cartService.createCartForUser(user.getId()));

        cartItemService.addOrUpdateItem(cart.getId(), productId, quantity);
        return ResponseEntity.ok("Đã thêm vào giỏ hàng.");
    }

    // ✅ Xoá sản phẩm khỏi giỏ (trả về JSON)
    @GetMapping("/delete/{itemId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long itemId, @RequestParam Long cartId) {
        cartItemService.removeItem(itemId);
        return ResponseEntity.ok("Đã xoá sản phẩm khỏi giỏ.");
    }
}
