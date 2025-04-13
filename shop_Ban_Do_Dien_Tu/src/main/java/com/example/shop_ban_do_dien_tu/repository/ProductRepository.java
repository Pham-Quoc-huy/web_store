package com.example.shop_ban_do_dien_tu.repository;

import com.example.shop_ban_do_dien_tu.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    // ✅ Tìm theo từ khóa và danh mục
    List<Product> findByCategoryIdAndNameContainingIgnoreCaseOrCategoryIdAndDescriptionContainingIgnoreCase(
            Long categoryId1, String keyword1, Long categoryId2, String keyword2
    );
    // ✅ Tìm theo từ khóa (không lọc danh mục)
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword1, String keyword2);
    List<Product> findByCategoryId(Long categoryId, Sort sort);

}
