package com.example.productmanagement.repository;

import com.example.productmanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Spring Data JPA generates implementation automatically!
    
    // Custom query methods (derived from method names)
    List<Product> findByCategory(String category);
    
    List<Product> findByNameContaining(String keyword);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Product> findByCategoryOrderByPriceAsc(String category);
    
    boolean existsByProductCode(String productCode);
    
    @Query("SELECT p FROM Product p " +
        "WHERE (:name IS NULL OR :name = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
        "AND (:category IS NULL OR :category = '' OR p.category = :category) " +
        "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
        "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> advancedSearch(@Param("name") String name,
                            @Param("category") String category,
                            @Param("minPrice") BigDecimal minPrice,
                            @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.category = :category")
    long countByCategory(@Param("category") String category);

    @Query("SELECT SUM(p.price * p.quantity) FROM Product p")
    BigDecimal calculateTotalValue();

    @Query("SELECT AVG(p.price) FROM Product p")
    BigDecimal calculateAveragePrice();

    @Query("SELECT p FROM Product p WHERE p.quantity < :threshold")
    List<Product> findLowStockProducts(@Param("threshold") int threshold);

    @Query("SELECT p FROM Product p ORDER BY p.id DESC")
    List<Product> findRecentProducts(Pageable pageable);

    // All basic CRUD methods inherited from JpaRepository:
    // - findAll()
    // - findById(Long id)
    // - save(Product product)
    // - deleteById(Long id)
    // - count()
    // - existsById(Long id)

    // Pattern: findBy + PropertyName + Condition

    // findByName(String name)                    // WHERE name = ?
    // findByNameContaining(String keyword)       // WHERE name LIKE %keyword%
    // findByNameStartingWith(String prefix)      // WHERE name LIKE prefix%
    // findByNameEndingWith(String suffix)        // WHERE name LIKE %suffix

    // findByPriceGreaterThan(BigDecimal price)   // WHERE price > ?
    // findByPriceLessThan(BigDecimal price)      // WHERE price < ?
    // findByPriceBetween(Double min, Double max) // WHERE price BETWEEN min AND max

    // findByCategory(String category)            // WHERE category = ?
    // findByCategoryAndPrice(String cat, Double p) // WHERE category = ? AND price = ?
    // findByCategoryOrName(String cat, String n)   // WHERE category = ? OR name = ?

    // findByCategoryOrderByPriceAsc(String cat)  // ORDER BY price ASC
    // findByCategoryOrderByPriceDesc(String cat) // ORDER BY price DESC

    // existsByProductCode(String code)           // Check if exists
    // countByCategory(String category)           // COUNT WHERE category = ?
    // deleteByCategory(String category)          // DELETE WHERE category = ?

}
