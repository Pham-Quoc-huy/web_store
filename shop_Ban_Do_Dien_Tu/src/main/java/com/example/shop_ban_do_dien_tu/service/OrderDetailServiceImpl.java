package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.OrderDetail;
import com.example.shop_ban_do_dien_tu.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    private final OrderDetailRepository repository;

    public OrderDetailServiceImpl(OrderDetailRepository repo) {
        this.repository = repo;
    }

    @Override
    public List<OrderDetail> getByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    @Override
    public OrderDetail save(OrderDetail detail) {
        return repository.save(detail);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
