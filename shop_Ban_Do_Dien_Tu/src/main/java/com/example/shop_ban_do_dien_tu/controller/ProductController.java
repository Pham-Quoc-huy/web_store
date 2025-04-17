package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Product;
import com.example.shop_ban_do_dien_tu.service.IProductService;
import com.example.shop_ban_do_dien_tu.service.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController  // Chuyển từ @Controller sang @RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;
    private final ICategoryService categoryService;

    public ProductController(IProductService productService, ICategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // ✅ Admin + User: Xem danh sách sản phẩm (trả về JSON)
    @GetMapping
    public ResponseEntity<List<Product>> listProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // Trả về danh sách sản phẩm dưới dạng JSON
    }

    // ✅ User: Xem chi tiết sản phẩm (trả về JSON)
    @GetMapping("/{id}")
    public ResponseEntity<Product> productDetails(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(product)) // Trả về sản phẩm dưới dạng JSON
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Admin: Tạo mới sản phẩm (trả về JSON)
    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct); // Trả về sản phẩm đã lưu dưới dạng JSON
    }

    // ✅ Admin: Xoá sản phẩm (trả về JSON thông báo)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Sản phẩm đã được xóa.");
    }

    // ✅ Tìm kiếm sản phẩm (trả về JSON)
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("q") String keyword,
                                                        @RequestParam(name = "categoryId", required = false) Long categoryId) {
        List<Product> results;
        if (categoryId != null) {
            results = productService.searchByKeywordAndCategory(keyword, categoryId);
        } else {
            results = productService.searchProducts(keyword);
        }
        return ResponseEntity.ok(results); // Trả về kết quả tìm kiếm dưới dạng JSON
    }

    // ✅ Lọc sản phẩm theo danh mục (trả về JSON)
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> listByCategory(@PathVariable Long categoryId,
                                                        @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort) {
        List<Product> products = productService.getProductsByCategorySorted(categoryId, sort);
        return ResponseEntity.ok(products); // Trả về sản phẩm theo danh mục dưới dạng JSON
    }
}
