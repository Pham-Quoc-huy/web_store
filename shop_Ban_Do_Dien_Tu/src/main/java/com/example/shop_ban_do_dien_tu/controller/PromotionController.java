package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Category;
import com.example.shop_ban_do_dien_tu.model.Promotion;
import com.example.shop_ban_do_dien_tu.service.ICategoryService;
import com.example.shop_ban_do_dien_tu.service.IPromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Chuyển từ @Controller sang @RestController
@RequestMapping("/admin/promotions")
public class PromotionController {

    private final IPromotionService promotionService;
    private final ICategoryService categoryService;

    public PromotionController(IPromotionService promotionService, ICategoryService categoryService) {
        this.promotionService = promotionService;
        this.categoryService = categoryService;
    }

    // ✅ Lấy danh sách khuyến mãi (trả về JSON)
    @GetMapping
    public ResponseEntity<List<Promotion>> listPromotions() {
        List<Promotion> promotions = promotionService.getAll();
        return ResponseEntity.ok(promotions); // Trả về danh sách khuyến mãi dưới dạng JSON
    }

    // ✅ Tạo mới khuyến mãi (trả về JSON thông báo)
    @PostMapping("/save")
    public ResponseEntity<String> savePromotion(@RequestBody Promotion promotion) {
        promotionService.create(promotion);
        return ResponseEntity.ok("Khuyến mãi đã được lưu.");
    }

    // ✅ Lấy danh mục sản phẩm (trả về JSON)
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories); // Trả về danh sách danh mục dưới dạng JSON
    }
}
