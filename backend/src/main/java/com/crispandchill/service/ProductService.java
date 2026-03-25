package com.crispandchill.service;

import com.crispandchill.model.Product;
import com.crispandchill.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * ProductService - Business logic for product operations
 */
@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Get all products
     * 
     * @return List of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    /**
     * Get all available products
     * 
     * @return List of available products only
     */
    public List<Product> getAvailableProducts() {
        return productRepository.findByIsAvailableTrue();
    }
    
    /**
     * Get product by ID
     * 
     * @param productId product ID
     * @return Product entity
     * @throws RuntimeException if product not found
     */
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    
    /**
     * Get products by category
     * 
     * @param categoryId category ID
     * @return List of products in that category
     */
    public List<Product> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }
    
    /**
     * Get available products by category
     * 
     * @param categoryId category ID
     * @return List of available products in that category
     */
    public List<Product> getAvailableProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryCategoryIdAndIsAvailableTrue(categoryId);
    }
    
    /**
     * Search products by name
     * 
     * @param productName product name (partial match)
     * @return List of matching products
     */
    public List<Product> searchProducts(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }
    
    /**
     * Create new product (admin function)
     * 
     * @param product product to create
     * @return saved Product entity
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    /**
     * Update product (admin function)
     * 
     * @param productId product ID to update
     * @param productDetails updated product details
     * @return updated Product entity
     * @throws RuntimeException if product not found
     */
    public Product updateProduct(Integer productId, Product productDetails) {
        Product product = getProductById(productId);
        
        product.setProductName(productDetails.getProductName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setImageUrl(productDetails.getImageUrl());
        product.setIsAvailable(productDetails.getIsAvailable());
        
        return productRepository.save(product);
    }
    
    /**
     * Delete product (admin function)
     * 
     * @param productId product ID to delete
     */
    public void deleteProduct(Integer productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }
}
