package com.example.productmanagement.service;

import com.example.productmanagement.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

public interface ProductService {

    List<Product> getAllProducts();          // all products without sort
    List<Product> getAllProducts(Sort sort); // all products with sort

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProduct(Long id);

    List<Product> searchProducts(String keyword);

    List<Product> getByCategory(String category);
    List<Product> getByCategory(String category, Sort sort);

    List<Product> advancedSearch(String name,
                                String category,
                                BigDecimal minPrice,
                                BigDecimal maxPrice);

    long countByCategory(String category);
    BigDecimal calculateTotalValue();
    BigDecimal calculateAveragePrice();
    List<Product> findLowStockProducts(int threshold);
    List<Product> findRecentProducts(int limit);

}
