package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Category;
import com.example.shop_ban_do_dien_tu.service.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Chuyển từ @Controller sang @RestController
@RequestMapping("/admin/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService service) {
        this.categoryService = service;
    }

    // ✅ Lấy tất cả danh mục (trả về JSON)
    @GetMapping
    public List<Category> listCategories() {
        return categoryService.getAllCategories();  // Trả về danh sách Category dưới dạng JSON
    }

    // ✅ Tạo mới danh mục (trả về JSON với thông báo)
    @PostMapping("/save")
    public ResponseEntity<String> saveCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
        return ResponseEntity.ok("Danh mục đã được lưu.");
    }

    // ✅ Lấy thông tin một danh mục (trả về JSON)
    @GetMapping("/edit/{id}")
    public ResponseEntity<Category> editCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(category -> ResponseEntity.ok(category))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Xoá danh mục (trả về JSON với thông báo)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Danh mục đã được xóa.");
    }
}
