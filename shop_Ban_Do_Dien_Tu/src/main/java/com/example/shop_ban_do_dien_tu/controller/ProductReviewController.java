package com.example.shop_ban_do_dien_tu.controller;

import com.example.shop_ban_do_dien_tu.model.Product;
import com.example.shop_ban_do_dien_tu.model.ProductReview;
import com.example.shop_ban_do_dien_tu.model.User;
import com.example.shop_ban_do_dien_tu.service.IProductReviewService;
import com.example.shop_ban_do_dien_tu.service.IProductService;
import com.example.shop_ban_do_dien_tu.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
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

    // ✅ USER gửi đánh giá
    @PostMapping("/add")
    public String addReview(@RequestParam Long userId,
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
        return "redirect:/products/" + productId + "?review_submitted";
    }

    // ✅ ADMIN xem & duyệt đánh giá
    @GetMapping("/admin/pending")
    public String pendingReviews(Model model) {
        model.addAttribute("pendingReviews", reviewService.getPendingReviews());
        return "review-list-pending";
    }

    // ✅ ADMIN cập nhật trạng thái
    @PostMapping("/admin/update-status")
    public String updateReviewStatus(@RequestParam Long reviewId,
                                     @RequestParam ProductReview.ReviewStatus status) {
        reviewService.updateStatus(reviewId, status);
        return "redirect:/reviews/admin/pending";
    }
}
