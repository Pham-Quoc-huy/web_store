package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.*;
import com.example.shop_ban_do_dien_tu.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartRepository cartRepository,
                            CartItemRepository cartItemRepository,
                            OrderDetailRepository orderDetailRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void checkout(Long userId, String paymentMethod, String shippingAddress) {
        Cart cart = cartRepository.findByUserId(userId)

                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
        System.out.println("Checkout userId = " + userId);

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        System.out.println("Số lượng sản phẩm trong giỏ: " + items.size());

        if (items.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        double total = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .paymentMethod(paymentMethod)
                .shippingAddress(shippingAddress)
                .status(Order.OrderStatus.PROCESSING)
                .totalAmount(total)
                .build();

        order = orderRepository.save(order);

        for (CartItem item : items) {
            OrderDetail detail = OrderDetail.builder()
                    .order(order)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .price(item.getProduct().getPrice())
                    .build();
            orderDetailRepository.save(detail);
        }

        cartItemRepository.deleteAll(items);
    }
}
