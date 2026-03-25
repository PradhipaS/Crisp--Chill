package com.crispandchill.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * SignupRequest DTO - Data Transfer Object for user signup
 * 
 * Why use DTOs?
 * - Separate API layer from database layer
 * - Control what data is accepted from frontend
 * - Add validation rules
 * - Don't expose internal entity structure
 * 
 * Validation annotations:
 * @NotBlank - field cannot be null or empty
 * @Email - must be valid email format
 * @Size - length constraints
 */
@Data
public class SignupRequest {
    
    /**
     * First name - required, 2-50 characters
     */
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    /**
     * Last name - required, 2-50 characters
     */
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    /**
     * Email - required, must be valid email format
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    
    /**
     * Password - required, minimum 6 characters
     * In production, enforce stronger password rules
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    /**
     * Phone - optional
     */
    @Size(max = 15, message = "Phone number must be at most 15 characters")
    private String phone;
}
