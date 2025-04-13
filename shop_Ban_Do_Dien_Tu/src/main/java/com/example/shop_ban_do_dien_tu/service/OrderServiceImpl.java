package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.*;
import com.example.shop_ban_do_dien_tu.repository.*;
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
    private final PromotionRepository promotionRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartRepository cartRepository,
                            CartItemRepository cartItemRepository,
                            OrderDetailRepository orderDetailRepository,
                            PromotionRepository promotionRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.promotionRepository = promotionRepository;
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
    public Order checkout(Long userId, String paymentMethod, String shippingAddress) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow();
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        if (items.isEmpty()) throw new IllegalStateException("Giỏ hàng trống");

        double total = 0.0;

        Order order = Order.builder()
                .user(cart.getUser())
                .orderDate(LocalDateTime.now())
                .paymentMethod(paymentMethod)
                .shippingAddress(shippingAddress)
                .status(Order.OrderStatus.PROCESSING)
                .totalAmount(0.0) // sẽ cập nhật sau
                .build();
        order = orderRepository.save(order);

        for (CartItem item : items) {
            Product product = item.getProduct();
            double price = product.getPrice();

            Optional<Promotion> promo = promotionRepository.findByCategoryIdAndActiveTrue(product.getCategory().getId());
            if (promo.isPresent() && isValid(promo.get())) {
                double discount = price * promo.get().getDiscountPercent() / 100;
                price -= discount;
            }

            total += price * item.getQuantity();

            orderDetailRepository.save(OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(price)
                    .build());
        }

        // ✅ Cập nhật tổng tiền
        order.setTotalAmount(total);
        orderRepository.save(order);

        cartItemRepository.deleteAll(items); // xoá giỏ hàng sau khi mua
        return order;
    }

    private boolean isValid(Promotion promo) {
        LocalDateTime now = LocalDateTime.now();
        return promo.getActive()
                && (promo.getStartDate() == null || !now.isBefore(promo.getStartDate()))
                && (promo.getEndDate() == null || !now.isAfter(promo.getEndDate()));
    }
}
