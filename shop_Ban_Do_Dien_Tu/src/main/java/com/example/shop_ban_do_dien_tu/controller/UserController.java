package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.User;
import com.example.shop_ban_do_dien_tu.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController  // Chuyển từ @Controller sang @RestController
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // ✅ USER: Hiển thị trang đăng nhập
    @GetMapping("/login")
    public ResponseEntity<String> showLogin() {
        return ResponseEntity.ok("Please provide username and password"); // Trả về thông báo yêu cầu đăng nhập
    }

    // ✅ USER: Thực hiện đăng nhập
    @PostMapping("/login")
    public ResponseEntity<String> doLogin(@RequestParam String username,
                                          @RequestParam String password,
                                          HttpSession session) {
        Optional<User> user = userService.login(username, password);
        if (user.isPresent()) {
            session.setAttribute("loggedInUser", user.get());
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    // ✅ USER: Hiển thị trang đăng ký
    @GetMapping("/register")
    public ResponseEntity<String> showRegister() {
        return ResponseEntity.ok("Please provide user details to register"); // Trả về thông báo yêu cầu thông tin đăng ký
    }

    // ✅ USER: Thực hiện đăng ký
    @PostMapping("/register")
    public ResponseEntity<String> doRegister(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok("Registration successful! Please log in.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ✅ USER: Đăng xuất
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }
}
