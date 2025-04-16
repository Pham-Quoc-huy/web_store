package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Product;
import com.example.shop_ban_do_dien_tu.service.IProductService;
import com.example.shop_ban_do_dien_tu.service.ICategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;
    private final ICategoryService categoryService;

    public ProductController(IProductService productService, ICategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // ✅ Admin + User: Xem danh sách
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    // ✅ User: Xem chi tiết
    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        productService.getProductById(id).ifPresent(product -> model.addAttribute("product", product));
        return "product-detail";
    }

    // ✅ Admin: Tạo mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product-form";
    }

    // ✅ Admin: Chỉnh sửa
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        productService.getProductById(id).ifPresent(product -> model.addAttribute("product", product));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product-form";
    }

    // ✅ Admin: Lưu (tạo + update)
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // ✅ Admin: Xóa
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
    @GetMapping("/search")
    public String searchProducts(@RequestParam("q") String keyword,
                                 @RequestParam(name = "categoryId", required = false) Long categoryId,
                                 Model model) {

        List<Product> results;
        if (categoryId != null) {
            results = productService.searchByKeywordAndCategory(keyword, categoryId);
        } else {
            results = productService.searchProducts(keyword);
        }

        model.addAttribute("products", results);
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("categories", categoryService.getAllCategories()); // cho dropdown
        return "product-list";
    }
    @GetMapping("/category/{categoryId}")
    public String listByCategory(@PathVariable Long categoryId,
                                 @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
                                 Model model) {
        model.addAttribute("products", productService.getProductsByCategorySorted(categoryId, sort));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("currentSort", sort);
        return "product-list";
    }

}
