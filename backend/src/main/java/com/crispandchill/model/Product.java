package com.crispandchill.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Product Entity - represents 'products' table
 * 
 * Stores all menu items with their details.
 * 
 * Relationships:
 * - Many products belong to one category (ManyToOne)
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    
    /**
     * Foreign key relationship to Category
     * @ManyToOne - many products can have same category
     * @JoinColumn - specifies foreign key column name
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * Price stored as BigDecimal for precision
     * precision = 10 means total digits
     * scale = 2 means digits after decimal (e.g., 12345678.90)
     */
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "image_url", length = 255)
    private String imageUrl;
    
    /**
     * Availability flag
     * true = available for order
     * false = out of stock
     */
    @Column(name = "is_available")
    private Boolean isAvailable = true;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
