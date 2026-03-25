package com.crispandchill.repository;

import com.crispandchill.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * CartRepository - Database access for Cart entity
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    
    /**
     * Find all cart items for a specific user
     * 
     * Generated SQL:
     * SELECT * FROM cart WHERE user_id = ?
     * 
     * @param userId user ID
     * @return List of cart items for that user
     */
    List<Cart> findByUserUserId(Integer userId);
    
    /**
     * Find specific cart item (user + product combination)
     * 
     * Used to check if product already in cart
     * 
     * Generated SQL:
     * SELECT * FROM cart WHERE user_id = ? AND product_id = ?
     * 
     * @param userId user ID
     * @param productId product ID
     * @return Optional<Cart> - cart item if exists
     */
    Optional<Cart> findByUserUserIdAndProductProductId(Integer userId, Integer productId);
    
    /**
     * Delete all cart items for a user
     * 
     * Used after order is placed to clear cart
     * 
     * Generated SQL:
     * DELETE FROM cart WHERE user_id = ?
     * 
     * @param userId user ID
     */
    void deleteByUserUserId(Integer userId);
    
    /**
     * Count items in user's cart
     * 
     * Generated SQL:
     * SELECT COUNT(*) FROM cart WHERE user_id = ?
     * 
     * @param userId user ID
     * @return number of items in cart
     */
    long countByUserUserId(Integer userId);
}
