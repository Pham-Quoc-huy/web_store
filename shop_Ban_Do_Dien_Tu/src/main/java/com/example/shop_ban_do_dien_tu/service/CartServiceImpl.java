package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.Cart;
import com.example.shop_ban_do_dien_tu.model.User;
import com.example.shop_ban_do_dien_tu.repository.CartRepository;
import com.example.shop_ban_do_dien_tu.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepo, UserRepository userRepo) {
        this.cartRepository = cartRepo;
        this.userRepository = userRepo;
    }

    @Override
    public Cart createCartForUser(Long userId) {
        if (cartRepository.existsByUserId(userId)) {
            return cartRepository.findByUserId(userId).orElseThrow();
        }
        User user = userRepository.findById(userId).orElseThrow();
        Cart cart = Cart.builder().user(user).build();
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
