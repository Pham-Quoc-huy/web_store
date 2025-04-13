package com.example.shop_ban_do_dien_tu.repository;

import com.example.shop_ban_do_dien_tu.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
