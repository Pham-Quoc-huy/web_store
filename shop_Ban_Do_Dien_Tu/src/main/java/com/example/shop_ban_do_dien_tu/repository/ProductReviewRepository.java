package com.example.shop_ban_do_dien_tu.repository;

import com.example.shop_ban_do_dien_tu.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByProductIdAndStatus(Long productId, ProductReview.ReviewStatus status);
    List<ProductReview> findByStatus(ProductReview.ReviewStatus status);
}
