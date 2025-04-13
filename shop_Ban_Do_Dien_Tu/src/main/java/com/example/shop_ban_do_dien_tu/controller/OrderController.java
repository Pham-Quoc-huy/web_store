package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Order;
import com.example.shop_ban_do_dien_tu.model.User;
import com.example.shop_ban_do_dien_tu.service.IOrderService;
import com.example.shop_ban_do_dien_tu.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService orderService;
    private final IUserService userService;

    public OrderController(IOrderService orderService, IUserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // ✅ ADMIN: xem toàn bộ đơn hàng
    @GetMapping("/admin")
    public String allOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order-list-admin";
    }

    // ✅ USER: xem lịch sử đơn hàng của mình
    @GetMapping("/user/{userId}")
    public String userOrders(@PathVariable Long userId, Model model) {
        model.addAttribute("orders", orderService.getOrdersByUser(userId));
        return "order-list-user";
    }

    // ✅ ADMIN: cập nhật trạng thái đơn hàng
    @GetMapping("/update-status/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam("status") Order.OrderStatus status) {
        orderService.getOrderById(id).ifPresent(order -> {
            order.setStatus(status);
            orderService.saveOrder(order);
        });
        return "redirect:/orders/admin";
    }

    // ✅ USER: tạo đơn hàng (giả định đã có userId)
    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order, @RequestParam Long userId) {
        User user = userService.getUserById(userId).orElseThrow();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PROCESSING);
        orderService.saveOrder(order);
        return "redirect:/orders/user/" + userId;
    }
    @PostMapping("/checkout")
    public String checkout(@RequestParam String paymentMethod,
                           @RequestParam String shippingAddress,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        orderService.checkout(user.getId(), paymentMethod, shippingAddress);

        redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công!");
        return "redirect:/orders/user/" + user.getId();
    }
}
