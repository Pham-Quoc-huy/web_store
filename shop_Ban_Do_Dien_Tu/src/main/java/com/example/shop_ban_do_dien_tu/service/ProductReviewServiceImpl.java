package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.ProductReview;
import com.example.shop_ban_do_dien_tu.repository.ProductReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements IProductReviewService {

    private final ProductReviewRepository reviewRepository;

    public ProductReviewServiceImpl(ProductReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ProductReview addReview(ProductReview review) {
        review.setStatus(ProductReview.ReviewStatus.PENDING);
        return reviewRepository.save(review);
    }

    @Override
    public List<ProductReview> getApprovedReviewsByProduct(Long productId) {
        return reviewRepository.findByProductIdAndStatus(productId, ProductReview.ReviewStatus.APPROVED);
    }

    @Override
    public List<ProductReview> getPendingReviews() {
        return reviewRepository.findByStatus(ProductReview.ReviewStatus.PENDING);
    }

    @Override
    public void updateStatus(Long reviewId, ProductReview.ReviewStatus status) {
        ProductReview review = reviewRepository.findById(reviewId).orElseThrow();
        review.setStatus(status);
        reviewRepository.save(review);
    }
}
