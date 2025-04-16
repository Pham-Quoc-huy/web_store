package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.service.IOrderDetailService;
import com.example.shop_ban_do_dien_tu.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order-details")
public class OrderDetailController {

    private final IOrderDetailService orderDetailService;
    private final IOrderService orderService;

    public OrderDetailController(IOrderDetailService detailService, IOrderService orderService) {
        this.orderDetailService = detailService;
        this.orderService = orderService;
    }

    // ✅ ADMIN + USER: xem chi tiết đơn hàng
    @GetMapping("/order/{orderId}")
    public String viewOrderDetails(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId).orElse(null));
        model.addAttribute("orderDetails", orderDetailService.getByOrderId(orderId));
        return "order-detail-list";
    }
}
