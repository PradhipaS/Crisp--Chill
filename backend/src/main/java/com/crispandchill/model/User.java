package com.crispandchill.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * User Entity - represents 'users' table in database
 * 
 * This class maps to the database table and stores user information.
 * 
 * Annotations explained:
 * @Entity - Marks this as a JPA entity (database table)
 * @Table - Specifies table name
 * @Data - Lombok: generates getters, setters, toString, equals, hashCode
 * @NoArgsConstructor - Lombok: generates no-argument constructor
 * @AllArgsConstructor - Lombok: generates constructor with all fields
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    /**
     * Primary key with auto-increment
     * @Id - marks as primary key
     * @GeneratedValue - auto-generates value
     * @Column - maps to database column
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
    /**
     * User's first name
     * nullable = false means this field is required
     */
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    /**
     * User's last name
     */
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    /**
     * User's email (must be unique)
     * unique = true ensures no duplicate emails
     */
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    /**
     * User's password (will be hashed before storing)
     * NEVER store plain text passwords!
     */
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    /**
     * User's phone number
     */
    @Column(name = "phone", length = 15)
    private String phone;
    
    /**
     * Timestamp when user was created
     * updatable = false means this won't change after creation
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when user was last updated
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Automatically set timestamps before saving
     * @PrePersist runs before first save
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Automatically update timestamp before updating
     * @PreUpdate runs before every update
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
