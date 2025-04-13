package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Promotion;
import com.example.shop_ban_do_dien_tu.service.ICategoryService;
import com.example.shop_ban_do_dien_tu.service.IPromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/promotions")
public class PromotionController {

    private final IPromotionService promotionService;
    private final ICategoryService categoryService;

    public PromotionController(IPromotionService promotionService, ICategoryService categoryService) {
        this.promotionService = promotionService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listPromotions(Model model) {
        model.addAttribute("promotions", promotionService.getAll());
        return "promotion-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("promotion", new Promotion());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "promotion-form";
    }

    @PostMapping("/save")
    public String savePromotion(@ModelAttribute Promotion promotion) {
        promotionService.create(promotion);
        return "redirect:/admin/promotions";
    }
}
