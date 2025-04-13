package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    void deleteUser(Long id);

    // ✅ Thêm chức năng auth
    Optional<User> login(String username, String password);
    User register(User user);
}