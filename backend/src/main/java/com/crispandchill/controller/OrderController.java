package com.crispandchill.controller;

import com.crispandchill.dto.OrderRequest;
import com.crispandchill.model.Order;
import com.crispandchill.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrderController - REST API endpoints for order operations
 * 
 * Base URL: /api/orders
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * Place order from cart
     * 
     * URL: POST /api/orders
     * Body: JSON with userId, paymentMethod, deliveryAddress
     * 
     * Example:
     * POST /api/orders
     * {
     *   "userId": 1,
     *   "paymentMethod": "Credit Card",
     *   "deliveryAddress": "123 Main St, City, State 12345"
     * }
     */
    @PostMapping
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest request) {
        try {
            Order order = orderService.placeOrder(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Order placed successfully");
            response.put("order", order);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Get all orders for a user
     * 
     * URL: GET /api/orders/user/{userId}
     * 
     * Example: GET /api/orders/user/1
     * Returns all orders for user with ID 1 (most recent first)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable Integer userId) {
        try {
            List<Order> orders = orderService.getUserOrders(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", orders.size());
            response.put("orders", orders);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Get order by ID
     * 
     * URL: GET /api/orders/{orderId}
     * 
     * Example: GET /api/orders/1
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("order", order);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Update order status (admin function)
     * 
     * URL: PUT /api/orders/{orderId}/status
     * Body: JSON with status
     * 
     * Example:
     * PUT /api/orders/1/status
     * {
     *   "status": "CONFIRMED"
     * }
     * 
     * Valid statuses: PENDING, CONFIRMED, PREPARING, DELIVERED, CANCELLED
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Integer orderId,
            @RequestBody Map<String, String> request) {
        try {
            String statusStr = request.get("status");
            Order.OrderStatus status = Order.OrderStatus.valueOf(statusStr);
            Order order = orderService.updateOrderStatus(orderId, status);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Order status updated");
            response.put("order", order);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid status value");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Update payment status (admin function)
     * 
     * URL: PUT /api/orders/{orderId}/payment
     * Body: JSON with status
     * 
     * Example:
     * PUT /api/orders/1/payment
     * {
     *   "status": "COMPLETED"
     * }
     * 
     * Valid statuses: PENDING, COMPLETED, FAILED
     */
    @PutMapping("/{orderId}/payment")
    public ResponseEntity<?> updatePaymentStatus(
            @PathVariable Integer orderId,
            @RequestBody Map<String, String> request) {
        try {
            String statusStr = request.get("status");
            Order.PaymentStatus status = Order.PaymentStatus.valueOf(statusStr);
            Order order = orderService.updatePaymentStatus(orderId, status);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Payment status updated");
            response.put("order", order);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid status value");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * Cancel order
     * 
     * URL: PUT /api/orders/{orderId}/cancel
     * 
     * Example: PUT /api/orders/1/cancel
     */
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer orderId) {
        try {
            Order order = orderService.cancelOrder(orderId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Order cancelled");
            response.put("order", order);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Get orders by status (admin function)
     * 
     * URL: GET /api/orders/status/{status}
     * 
     * Example: GET /api/orders/status/PENDING
     * Returns all pending orders
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status);
            List<Order> orders = orderService.getOrdersByStatus(orderStatus);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", orders.size());
            response.put("orders", orders);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid status value");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
