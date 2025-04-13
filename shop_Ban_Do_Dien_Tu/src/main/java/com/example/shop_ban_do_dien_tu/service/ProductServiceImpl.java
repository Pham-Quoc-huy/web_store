package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.Product;
import com.example.shop_ban_do_dien_tu.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository repo) {
        this.productRepository = repo;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<Product> searchByKeywordAndCategory(String keyword, Long categoryId) {
        return productRepository.findByCategoryIdAndNameContainingIgnoreCaseOrCategoryIdAndDescriptionContainingIgnoreCase(
                categoryId, keyword, categoryId, keyword);
    }
    @Override
    public List<Product> getProductsByCategorySorted(Long categoryId, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ?
                Sort.by("price").ascending() :
                Sort.by("price").descending();
        return productRepository.findByCategoryId(categoryId, sort);
    }


}
