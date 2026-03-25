package com.crispandchill.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * CartRequest DTO - Data Transfer Object for cart operations
 * 
 * Used when adding/updating items in cart
 */
@Data
public class CartRequest {
    
    /**
     * User ID - who is adding to cart
     */
    @NotNull(message = "User ID is required")
    private Integer userId;
    
    /**
     * Product ID - which product to add
     */
    @NotNull(message = "Product ID is required")
    private Integer productId;
    
    /**
     * Quantity - how many items
     * Must be at least 1
     */
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
