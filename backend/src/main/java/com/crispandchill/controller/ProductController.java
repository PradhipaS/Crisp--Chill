package com.crispandchill.controller;

import com.crispandchill.model.Product;
import com.crispandchill.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProductController - REST API endpoints for product operations
 * 
 * Base URL: /api/products
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * Get all products
     * 
     * URL: GET /api/products
     * 
     * Returns all products in database
     * 
     * Example: GET /api/products
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", products.size());
            response.put("products", products);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Get available products only
     * 
     * URL: GET /api/products/available
     * 
     * Returns only products where is_available = true
     * 
     * Example: GET /api/products/available
     */
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableProducts() {
        try {
            List<Product> products = productService.getAvailableProducts();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", products.size());
            response.put("products", products);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Get product by ID
     * 
     * URL: GET /api/products/{id}
     * 
     * Example: GET /api/products/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            Product product = productService.getProductById(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("product", product);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Get products by category
     * 
     * URL: GET /api/products/category/{categoryId}
     * 
     * Example: GET /api/products/category/1
     * Returns all ice cream products (if category 1 is ice cream)
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Integer categoryId) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", products.size());
            response.put("products", products);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Search products by name
     * 
     * URL: GET /api/products/search?name=chocolate
     * 
     * @RequestParam extracts query parameter from URL
     * 
     * Example: GET /api/products/search?name=chocolate
     * Returns all products with "chocolate" in name
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String name) {
        try {
            List<Product> products = productService.searchProducts(name);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", products.size());
            response.put("products", products);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Create new product (admin function)
     * 
     * URL: POST /api/products
     * Body: JSON with product details
     * 
     * Example:
     * POST /api/products
     * {
     *   "productName": "Mango Icecream",
     *   "description": "Fresh mango flavor",
     *   "price": 95.00,
     *   "imageUrl": "Images/mango.jpg",
     *   "isAvailable": true,
     *   "category": { "categoryId": 1 }
     * }
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.createProduct(product);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Product created successfully");
            response.put("product", savedProduct);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Update product (admin function)
     * 
     * URL: PUT /api/products/{id}
     * Body: JSON with updated product details
     * 
     * Example: PUT /api/products/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Product updated successfully");
            response.put("product", updatedProduct);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Delete product (admin function)
     * 
     * URL: DELETE /api/products/{id}
     * 
     * Example: DELETE /api/products/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Product deleted successfully");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
