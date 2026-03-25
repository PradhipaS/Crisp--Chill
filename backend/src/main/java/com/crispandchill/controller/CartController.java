package com.crispandchill.controller;

import com.crispandchill.dto.CartRequest;
import com.crispandchill.model.Cart;
import com.crispandchill.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CartController - REST API endpoints for cart operations
 * 
 * Base URL: /api/cart
 */
@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    /**
     * Add item to cart
     * 
     * URL: POST /api/cart
     * Body: JSON with userId, productId, quantity
     * 
     * Example:
     * POST /api/cart
     * {
     *   "userId": 1,
     *   "productId": 5,
     *   "quantity": 2
     * }
     */
    @PostMapping
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartRequest request) {
        try {
            Cart cart = cartService.addToCart(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Item added to cart");
            response.put("cart", cart);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Get cart items for a user
     * 
     * URL: GET /api/cart/user/{userId}
     * 
     * Example: GET /api/cart/user/1
     * Returns all cart items for user with ID 1
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartItems(@PathVariable Integer userId) {
        try {
            List<Cart> cartItems = cartService.getCartItems(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", cartItems.size());
            response.put("cartItems", cartItems);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Update cart item quantity
     * 
     * URL: PUT /api/cart/{cartId}
     * Body: JSON with quantity
     * 
     * Example:
     * PUT /api/cart/1
     * {
     *   "quantity": 3
     * }
     */
    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCartQuantity(
            @PathVariable Integer cartId,
            @RequestBody Map<String, Integer> request) {
        try {
            Integer quantity = request.get("quantity");
            Cart cart = cartService.updateCartQuantity(cartId, quantity);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cart updated");
            response.put("cart", cart);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Remove item from cart
     * 
     * URL: DELETE /api/cart/{cartId}
     * 
     * Example: DELETE /api/cart/1
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Integer cartId) {
        try {
            cartService.removeFromCart(cartId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Item removed from cart");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Clear all cart items for a user
     * 
     * URL: DELETE /api/cart/user/{userId}
     * 
     * Example: DELETE /api/cart/user/1
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Integer userId) {
        try {
            cartService.clearCart(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cart cleared");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Get cart count for a user
     * 
     * URL: GET /api/cart/user/{userId}/count
     * 
     * Example: GET /api/cart/user/1/count
     * Returns: { "count": 5 }
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<?> getCartCount(@PathVariable Integer userId) {
        try {
            long count = cartService.getCartCount(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", count);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
