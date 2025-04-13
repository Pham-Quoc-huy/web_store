package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetail> getByOrderId(Long orderId);
    OrderDetail save(OrderDetail detail);
    void delete(Long id);
}
