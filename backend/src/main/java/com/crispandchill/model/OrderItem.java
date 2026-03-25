package com.crispandchill.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * OrderItem Entity - represents 'order_items' table
 * 
 * Stores individual products within an order.
 * 
 * Why separate from Order?
 * - One order can have multiple products
 * - Need to store quantity and price at time of order
 * - Price might change later, but order should show original price
 * 
 * Relationships:
 * - Each order item belongs to one order
 * - Each order item references one product
 */
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;
    
    /**
     * Foreign key to Order
     * Which order does this item belong to
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    /**
     * Foreign key to Product
     * Which product was ordered
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    /**
     * Quantity ordered
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    /**
     * Price at time of order
     * Important: Store price here because product price might change
     * This ensures order history shows correct price paid
     */
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
