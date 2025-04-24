package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.Cart;
import com.example.shop_ban_do_dien_tu.model.CartItem;
import com.example.shop_ban_do_dien_tu.model.Product;
import com.example.shop_ban_do_dien_tu.repository.CartItemRepository;
import com.example.shop_ban_do_dien_tu.repository.CartRepository;
import com.example.shop_ban_do_dien_tu.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository,
                               ProductRepository productRepository,
                               CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<CartItem> getItemsByCart(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    @Override
    public CartItem addOrUpdateItem(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        return cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .map(existingItem -> {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    return cartItemRepository.save(existingItem);
                })
                .orElseGet(() -> cartItemRepository.save(CartItem.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(quantity)
                        .build()));
    }

    @Override
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    @Override
    public void updateQuantity(Long itemId, int quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

}
