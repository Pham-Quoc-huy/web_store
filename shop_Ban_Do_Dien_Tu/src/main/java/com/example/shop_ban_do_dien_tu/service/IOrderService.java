package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> getAllOrders();
    List<Order> getOrdersByUser(Long userId);
    Optional<Order> getOrderById(Long id);
    Order saveOrder(Order order);
    void deleteOrder(Long id);
    Order checkout(Long userId, String paymentMethod, String shippingAddress);

}
