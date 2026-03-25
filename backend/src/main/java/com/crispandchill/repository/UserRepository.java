package com.crispandchill.repository;

import com.crispandchill.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * UserRepository - Database access for User entity
 * 
 * Extends JpaRepository which provides:
 * - save() - insert or update user
 * - findById() - find user by ID
 * - findAll() - get all users
 * - deleteById() - delete user
 * - count() - count users
 * And many more...
 * 
 * JpaRepository<User, Integer> means:
 * - User: Entity type
 * - Integer: Primary key type
 * 
 * @Repository marks this as a data access component
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    /**
     * Find user by email
     * 
     * Spring Data JPA automatically implements this method
     * based on method name convention:
     * - findBy: query method
     * - Email: field name in User entity
     * 
     * Generated SQL: SELECT * FROM users WHERE email = ?
     * 
     * @param email user's email
     * @return Optional<User> - may or may not contain user
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if email already exists
     * 
     * Useful for signup validation
     * Generated SQL: SELECT COUNT(*) > 0 FROM users WHERE email = ?
     * 
     * @param email email to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Find user by email and password
     * 
     * Used for login authentication
     * Generated SQL: SELECT * FROM users WHERE email = ? AND password = ?
     * 
     * Note: In production, use proper password hashing (BCrypt)
     * 
     * @param email user's email
     * @param password user's password (should be hashed)
     * @return Optional<User> - user if credentials match
     */
    Optional<User> findByEmailAndPassword(String email, String password);
}
