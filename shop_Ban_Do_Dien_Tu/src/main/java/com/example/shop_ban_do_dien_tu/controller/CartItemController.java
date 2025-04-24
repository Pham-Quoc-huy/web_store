package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Cart;
import com.example.shop_ban_do_dien_tu.model.CartItem;
import com.example.shop_ban_do_dien_tu.model.Product;
import com.example.shop_ban_do_dien_tu.service.ICartItemService;
import com.example.shop_ban_do_dien_tu.service.ICartService;
import com.example.shop_ban_do_dien_tu.service.IProductService;
import com.example.shop_ban_do_dien_tu.dto.CartItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IProductService productService;

    public CartItemController(ICartItemService itemService, ICartService cartService, IProductService productService) {
        this.cartItemService = itemService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> viewCartItems(@PathVariable Long cartId) {
        List<CartItem> items = cartItemService.getItemsByCart(cartId);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemRequest request) {
        Long userId = request.getUserId();
        Long productId = request.getProductId();
        int quantity = request.getQuantity();

        Cart cart = cartService.getCartByUserId(userId)
                .orElseGet(() -> cartService.createCartForUser(userId));

        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        cartItemService.addOrUpdateItem(cart.getId(), product.getId(), quantity);
        return ResponseEntity.ok("Đã thêm vào giỏ hàng.");
    }

    @PutMapping("/update/{itemId}")
    public ResponseEntity<String> updateQuantity(@PathVariable Long itemId, @RequestBody Map<String, Integer> body) {
        int quantity = body.get("quantity");
        cartItemService.updateQuantity(itemId, quantity);
        return ResponseEntity.ok("Đã cập nhật số lượng");
    }

    @GetMapping("/delete/{itemId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long itemId, @RequestParam Long cartId) {
        cartItemService.removeItem(itemId);
        return ResponseEntity.ok("Đã xoá sản phẩm khỏi giỏ.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> getCartItemsByUserId(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId)
                .map(cart -> ResponseEntity.ok(cartItemService.getItemsByCart(cart.getId())))
                .orElse(ResponseEntity.ok(List.of()));
    }

}
