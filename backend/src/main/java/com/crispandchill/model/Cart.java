package com.crispandchill.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Cart Entity - represents 'cart' table
 * 
 * Stores items that users add to cart before checkout.
 * Temporary storage - cleared after order is placed.
 * 
 * Relationships:
 * - Each cart item belongs to one user
 * - Each cart item references one product
 */
@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;
    
    /**
     * Foreign key to User
     * Links cart item to specific user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * Foreign key to Product
     * Links cart item to specific product
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    /**
     * Quantity of this product in cart
     * Default is 1
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;
    
    /**
     * When item was added to cart
     */
    @Column(name = "added_at", updatable = false)
    private LocalDateTime addedAt;
    
    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }
}
