package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Product;
import com.example.shop_ban_do_dien_tu.model.ProductReview;
import com.example.shop_ban_do_dien_tu.model.User;
import com.example.shop_ban_do_dien_tu.service.IProductReviewService;
import com.example.shop_ban_do_dien_tu.service.IProductService;
import com.example.shop_ban_do_dien_tu.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Chuyển từ @Controller sang @RestController
@RequestMapping("/reviews")
public class ProductReviewController {

    private final IProductReviewService reviewService;
    private final IProductService productService;
    private final IUserService userService;

    public ProductReviewController(IProductReviewService reviewService, IProductService productService, IUserService userService) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.userService = userService;
    }

    // ✅ USER gửi đánh giá (trả về JSON)
    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestParam Long userId,
                                            @RequestParam Long productId,
                                            @RequestParam Integer rating,
                                            @RequestParam String comment) {
        User user = userService.getUserById(userId).orElseThrow();
        Product product = productService.getProductById(productId).orElseThrow();

        ProductReview review = ProductReview.builder()
                .user(user)
                .product(product)
                .rating(rating)
                .comment(comment)
                .build();

        reviewService.addReview(review);
        return ResponseEntity.ok("Đánh giá đã được gửi thành công.");
    }

    // ✅ ADMIN xem & duyệt đánh giá (trả về JSON)
    @GetMapping("/admin/pending")
    public ResponseEntity<List<ProductReview>> pendingReviews() {
        List<ProductReview> pendingReviews = reviewService.getPendingReviews();
        return ResponseEntity.ok(pendingReviews); // Trả về danh sách đánh giá chưa duyệt dưới dạng JSON
    }

    // ✅ ADMIN cập nhật trạng thái đánh giá (trả về JSON thông báo)
    @PostMapping("/admin/update-status")
    public ResponseEntity<String> updateReviewStatus(@RequestParam Long reviewId,
                                                     @RequestParam ProductReview.ReviewStatus status) {
        reviewService.updateStatus(reviewId, status);
        return ResponseEntity.ok("Trạng thái đánh giá đã được cập nhật.");
    }
}
