package com.example.shop_ban_do_dien_tu.repository;

import com.example.shop_ban_do_dien_tu.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
