package com.crispandchill.repository;

import com.crispandchill.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ProductRepository - Database access for Product entity
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    /**
     * Find all products by category ID
     * 
     * Generated SQL: 
     * SELECT * FROM products WHERE category_id = ?
     * 
     * @param categoryId category ID
     * @return List of products in that category
     */
    List<Product> findByCategoryCategoryId(Integer categoryId);
    
    /**
     * Find all available products
     * 
     * Generated SQL:
     * SELECT * FROM products WHERE is_available = true
     * 
     * @return List of available products
     */
    List<Product> findByIsAvailableTrue();
    
    /**
     * Find products by name (case-insensitive, partial match)
     * 
     * Generated SQL:
     * SELECT * FROM products WHERE product_name LIKE %?%
     * 
     * @param productName product name to search
     * @return List of matching products
     */
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    
    /**
     * Find available products by category
     * 
     * Combines category filter and availability filter
     * 
     * @param categoryId category ID
     * @return List of available products in category
     */
    List<Product> findByCategoryCategoryIdAndIsAvailableTrue(Integer categoryId);
}
