package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category saveCategory(Category category);
    void deleteCategory(Long id);
}
