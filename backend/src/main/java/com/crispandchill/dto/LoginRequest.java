package com.crispandchill.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * LoginRequest DTO - Data Transfer Object for user login
 * 
 * Accepts email and password from frontend
 */
@Data
public class LoginRequest {
    
    /**
     * Email - required, must be valid email
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    
    /**
     * Password - required
     */
    @NotBlank(message = "Password is required")
    private String password;
}
