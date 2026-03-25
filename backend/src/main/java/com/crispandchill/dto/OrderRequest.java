package com.crispandchill.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * OrderRequest DTO - Data Transfer Object for placing orders
 * 
 * Contains order details from frontend
 */
@Data
public class OrderRequest {
    
    /**
     * User ID - who is placing the order
     */
    @NotNull(message = "User ID is required")
    private Integer userId;
    
    /**
     * Payment method - how user will pay
     * e.g., "Credit Card", "Cash on Delivery", "UPI"
     */
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    /**
     * Delivery address - where to deliver
     */
    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;
}
