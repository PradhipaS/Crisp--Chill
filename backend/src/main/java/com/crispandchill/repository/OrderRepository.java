package com.crispandchill.repository;

import com.crispandchill.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * OrderRepository - Database access for Order entity
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    
    /**
     * Find all orders for a specific user
     * Ordered by most recent first
     * 
     * Generated SQL:
     * SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC
     * 
     * @param userId user ID
     * @return List of orders for that user
     */
    List<Order> findByUserUserIdOrderByOrderDateDesc(Integer userId);
    
    /**
     * Find orders by status
     * 
     * Useful for admin to see pending/confirmed orders
     * 
     * @param orderStatus order status
     * @return List of orders with that status
     */
    List<Order> findByOrderStatus(Order.OrderStatus orderStatus);
    
    /**
     * Find orders by payment status
     * 
     * Useful to track pending payments
     * 
     * @param paymentStatus payment status
     * @return List of orders with that payment status
     */
    List<Order> findByPaymentStatus(Order.PaymentStatus paymentStatus);
    
    /**
     * Count total orders for a user
     * 
     * @param userId user ID
     * @return number of orders
     */
    long countByUserUserId(Integer userId);
}
