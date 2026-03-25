package com.crispandchill.service;

import com.crispandchill.dto.OrderRequest;
import com.crispandchill.model.*;
import com.crispandchill.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * OrderService - Business logic for order operations
 */
@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Place order from cart
     * 
     * Steps:
     * 1. Get user's cart items
     * 2. Calculate total amount
     * 3. Create order
     * 4. Create order items from cart
     * 5. Clear cart
     * 
     * @param request order request with userId, paymentMethod, deliveryAddress
     * @return created Order entity
     * @throws RuntimeException if cart is empty
     */
    public Order placeOrder(OrderRequest request) {
        // Get user
        User user = userService.getUserById(request.getUserId());
        
        // Get cart items
        List<Cart> cartItems = cartService.getCartItems(request.getUserId());
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        // Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart cartItem : cartItems) {
            BigDecimal itemTotal = cartItem.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }
        
        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(Order.OrderStatus.PENDING);
        order.setPaymentStatus(Order.PaymentStatus.PENDING);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setDeliveryAddress(request.getDeliveryAddress());
        
        // Save order first to get order ID
        order = orderRepository.save(order);
        
        // Create order items from cart
        for (Cart cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice()); // Store current price
            
            order.getOrderItems().add(orderItem);
        }
        
        // Save order with items
        order = orderRepository.save(order);
        
        // Clear cart
        cartService.clearCart(request.getUserId());
        
        return order;
    }
    
    /**
     * Get all orders for a user
     * 
     * @param userId user ID
     * @return List of orders (most recent first)
     */
    public List<Order> getUserOrders(Integer userId) {
        return orderRepository.findByUserUserIdOrderByOrderDateDesc(userId);
    }
    
    /**
     * Get order by ID
     * 
     * @param orderId order ID
     * @return Order entity
     * @throws RuntimeException if order not found
     */
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    
    /**
     * Update order status (admin function)
     * 
     * @param orderId order ID
     * @param status new order status
     * @return updated Order entity
     */
    public Order updateOrderStatus(Integer orderId, Order.OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
    
    /**
     * Update payment status (admin function)
     * 
     * @param orderId order ID
     * @param status new payment status
     * @return updated Order entity
     */
    public Order updatePaymentStatus(Integer orderId, Order.PaymentStatus status) {
        Order order = getOrderById(orderId);
        order.setPaymentStatus(status);
        return orderRepository.save(order);
    }
    
    /**
     * Cancel order
     * 
     * @param orderId order ID
     * @return updated Order entity
     * @throws RuntimeException if order cannot be cancelled
     */
    public Order cancelOrder(Integer orderId) {
        Order order = getOrderById(orderId);
        
        // Can only cancel pending or confirmed orders
        if (order.getOrderStatus() == Order.OrderStatus.PREPARING ||
            order.getOrderStatus() == Order.OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel order in " + order.getOrderStatus() + " status");
        }
        
        order.setOrderStatus(Order.OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
    
    /**
     * Get all orders by status (admin function)
     * 
     * @param status order status
     * @return List of orders with that status
     */
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }
}
