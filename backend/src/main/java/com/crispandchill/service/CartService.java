package com.crispandchill.service;

import com.crispandchill.dto.CartRequest;
import com.crispandchill.model.Cart;
import com.crispandchill.model.Product;
import com.crispandchill.model.User;
import com.crispandchill.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * CartService - Business logic for cart operations
 * 
 * @Transactional ensures database operations are atomic
 * (all succeed or all fail together)
 */
@Service
@Transactional
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    /**
     * Add item to cart
     * 
     * Logic:
     * 1. Check if product already in cart
     * 2. If yes, update quantity
     * 3. If no, create new cart item
     * 
     * @param request cart request with userId, productId, quantity
     * @return saved Cart entity
     */
    public Cart addToCart(CartRequest request) {
        // Get user and product (throws exception if not found)
        User user = userService.getUserById(request.getUserId());
        Product product = productService.getProductById(request.getProductId());
        
        // Check if product already in cart
        Optional<Cart> existingCart = cartRepository.findByUserUserIdAndProductProductId(
            request.getUserId(), 
            request.getProductId()
        );
        
        if (existingCart.isPresent()) {
            // Update quantity
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
            return cartRepository.save(cart);
        } else {
            // Create new cart item
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(request.getQuantity());
            return cartRepository.save(cart);
        }
    }
    
    /**
     * Get all cart items for a user
     * 
     * @param userId user ID
     * @return List of cart items
     */
    public List<Cart> getCartItems(Integer userId) {
        return cartRepository.findByUserUserId(userId);
    }
    
    /**
     * Update cart item quantity
     * 
     * @param cartId cart item ID
     * @param quantity new quantity
     * @return updated Cart entity
     * @throws RuntimeException if cart item not found
     */
    public Cart updateCartQuantity(Integer cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
        
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }
    
    /**
     * Remove item from cart
     * 
     * @param cartId cart item ID to remove
     */
    public void removeFromCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartRepository.delete(cart);
    }
    
    /**
     * Clear all cart items for a user
     * 
     * Used after order is placed
     * 
     * @param userId user ID
     */
    public void clearCart(Integer userId) {
        cartRepository.deleteByUserUserId(userId);
    }
    
    /**
     * Get cart item count for a user
     * 
     * @param userId user ID
     * @return number of items in cart
     */
    public long getCartCount(Integer userId) {
        return cartRepository.countByUserUserId(userId);
    }
}
