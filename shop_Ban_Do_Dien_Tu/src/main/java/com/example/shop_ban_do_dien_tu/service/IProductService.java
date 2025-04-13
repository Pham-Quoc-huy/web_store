package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(Long categoryId);
    Optional<Product> getProductById(Long id);
    Product saveProduct(Product product);
    void deleteProduct(Long id);
    List<Product> searchProducts(String keyword);
    List<Product> searchByKeywordAndCategory(String keyword, Long categoryId);
    List<Product> getProductsByCategorySorted(Long categoryId, String sortDirection);

}
