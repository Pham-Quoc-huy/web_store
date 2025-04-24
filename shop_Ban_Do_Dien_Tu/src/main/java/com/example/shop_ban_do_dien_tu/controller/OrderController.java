package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.dto.CheckoutRequest;
import com.example.shop_ban_do_dien_tu.model.Order;
import com.example.shop_ban_do_dien_tu.model.User;
import com.example.shop_ban_do_dien_tu.service.IOrderService;
import com.example.shop_ban_do_dien_tu.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@RestController  // Chuyển từ @Controller sang @RestController
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService orderService;
    private final IUserService userService;

    public OrderController(IOrderService orderService, IUserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // ✅ ADMIN: xem toàn bộ đơn hàng (trả về JSON)
    @GetMapping("/admin")
    public List<Order> allOrders() {
        return orderService.getAllOrders(); // Trả về danh sách đơn hàng dưới dạng JSON
    }

    // ✅ USER: xem lịch sử đơn hàng của mình (trả về JSON)
    @GetMapping("/user/{userId}")
    public List<Order> userOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId); // Trả về danh sách đơn hàng của người dùng dưới dạng JSON
    }

    // ✅ ADMIN: cập nhật trạng thái đơn hàng (trả về JSON với thông báo)
    @GetMapping("/update-status/{id}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestParam("status") Order.OrderStatus status) {
        orderService.getOrderById(id).ifPresent(order -> {
            order.setStatus(status);
            orderService.saveOrder(order);
        });
        return ResponseEntity.ok("Trạng thái đơn hàng đã được cập nhật.");
    }

    // ✅ USER: tạo đơn hàng (trả về JSON)
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestParam Long userId) {
        User user = userService.getUserById(userId).orElseThrow();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PROCESSING);
        order = orderService.saveOrder(order);
        return ResponseEntity.ok(order); // Trả về đơn hàng đã tạo dưới dạng JSON
    }

    // ✅ USER: thanh toán và tạo đơn hàng (trả về JSON với thông báo)
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody CheckoutRequest request) {
        orderService.checkout(
                request.getUserId(),
                request.getPaymentMethod(),
                request.getShippingAddress()
        );
        return ResponseEntity.ok("Đặt hàng thành công!");
    }

}
