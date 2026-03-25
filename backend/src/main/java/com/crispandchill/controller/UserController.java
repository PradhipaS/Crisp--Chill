package com.crispandchill.controller;

import com.crispandchill.dto.LoginRequest;
import com.crispandchill.dto.SignupRequest;
import com.crispandchill.model.User;
import com.crispandchill.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * UserController - REST API endpoints for user operations
 * 
 * @RestController combines @Controller and @ResponseBody
 * - Returns JSON instead of HTML views
 * 
 * @RequestMapping("/api/users") sets base URL for all endpoints
 * - All endpoints start with /api/users
 * 
 * @CrossOrigin allows frontend (different port) to access API
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow all origins (configure properly in production)
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Signup endpoint
     * 
     * URL: POST /api/users/signup
     * Body: JSON with firstName, lastName, email, password, phone
     * 
     * @PostMapping handles HTTP POST requests
     * @RequestBody converts JSON to Java object
     * @Valid validates input using annotations in SignupRequest
     * 
     * Returns:
     * - 201 CREATED with user data if successful
     * - 400 BAD REQUEST if validation fails or email exists
     * 
     * Example request:
     * POST /api/users/signup
     * {
     *   "firstName": "John",
     *   "lastName": "Doe",
     *   "email": "john@example.com",
     *   "password": "password123",
     *   "phone": "1234567890"
     * }
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        try {
            User user = userService.registerUser(request);
            
            // Create response without password
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("user", createUserResponse(user));
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Login endpoint
     * 
     * URL: POST /api/users/login
     * Body: JSON with email and password
     * 
     * Returns:
     * - 200 OK with user data if credentials valid
     * - 401 UNAUTHORIZED if credentials invalid
     * 
     * Example request:
     * POST /api/users/login
     * {
     *   "email": "john@example.com",
     *   "password": "password123"
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userService.loginUser(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("user", createUserResponse(user));
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
    
    /**
     * Get user by ID endpoint
     * 
     * URL: GET /api/users/{id}
     * 
     * @PathVariable extracts {id} from URL
     * 
     * Example: GET /api/users/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("user", createUserResponse(user));
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Helper method to create user response without password
     * 
     * NEVER send password to frontend!
     */
    private Map<String, Object> createUserResponse(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", user.getUserId());
        userMap.put("firstName", user.getFirstName());
        userMap.put("lastName", user.getLastName());
        userMap.put("email", user.getEmail());
        userMap.put("phone", user.getPhone());
        userMap.put("createdAt", user.getCreatedAt());
        return userMap;
    }
}
