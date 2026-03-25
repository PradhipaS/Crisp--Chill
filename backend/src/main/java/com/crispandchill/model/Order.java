package com.crispandchill.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order Entity - represents 'orders' table
 * 
 * Stores completed orders with status and payment info.
 * 
 * Relationships:
 * - Each order belongs to one user
 * - Each order has many order items (OneToMany)
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;
    
    /**
     * Foreign key to User
     * Who placed this order
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * Total amount for this order
     */
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    /**
     * Order status enum
     * Tracks order lifecycle: pending → confirmed → preparing → delivered
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 20)
    private OrderStatus orderStatus = OrderStatus.PENDING;
    
    /**
     * Payment status enum
     * Tracks payment: pending → completed or failed
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 20)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    /**
     * Payment method used
     * e.g., "Credit Card", "Cash on Delivery", "UPI"
     */
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;
    
    /**
     * Delivery address
     */
    @Column(name = "delivery_address", columnDefinition = "TEXT")
    private String deliveryAddress;
    
    /**
     * When order was placed
     */
    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;
    
    /**
     * Last update timestamp
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * List of items in this order
     * OneToMany relationship with OrderItem
     * cascade = ALL means operations on Order affect OrderItems
     * orphanRemoval = true means deleting order deletes items
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Order Status Enum
     * Defines possible order states
     */
    public enum OrderStatus {
        PENDING,      // Order placed, awaiting confirmation
        CONFIRMED,    // Order confirmed by restaurant
        PREPARING,    // Food is being prepared
        DELIVERED,    // Order delivered to customer
        CANCELLED     // Order cancelled
    }
    
    /**
     * Payment Status Enum
     * Defines possible payment states
     */
    public enum PaymentStatus {
        PENDING,      // Payment not yet made
        COMPLETED,    // Payment successful
        FAILED        // Payment failed
    }
}
