package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.ProductReview;

import java.util.List;

public interface IProductReviewService {
    ProductReview addReview(ProductReview review);
    List<ProductReview> getApprovedReviewsByProduct(Long productId);
    List<ProductReview> getPendingReviews();
    void updateStatus(Long reviewId, ProductReview.ReviewStatus status);
}
