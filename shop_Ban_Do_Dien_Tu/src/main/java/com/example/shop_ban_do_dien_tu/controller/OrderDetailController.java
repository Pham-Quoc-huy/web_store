package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Order;
import com.example.shop_ban_do_dien_tu.service.IOrderDetailService;
import com.example.shop_ban_do_dien_tu.service.IOrderService;
import com.example.shop_ban_do_dien_tu.model.OrderDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController  // Chuyển từ @Controller sang @RestController
@RequestMapping("/order-details")
public class OrderDetailController {

    private final IOrderDetailService orderDetailService;
    private final IOrderService orderService;

    public OrderDetailController(IOrderDetailService detailService, IOrderService orderService) {
        this.orderDetailService = detailService;
        this.orderService = orderService;
    }

    // ✅ ADMIN + USER: xem chi tiết đơn hàng (trả về JSON)
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Map<String, Object>> viewOrderDetails(@PathVariable Long orderId) {
        // Lấy order và orderDetails
        Optional<Order> order = orderService.getOrderById(orderId);
        List<OrderDetail> orderDetails = orderDetailService.getByOrderId(orderId);

        // Kiểm tra nếu không tìm thấy đơn hàng
        if (!order.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Tạo response data
        Map<String, Object> response = new HashMap<>();
        response.put("order", order.get());
        response.put("orderDetails", orderDetails);

        return ResponseEntity.ok(response); // Trả về JSON
    }
}
