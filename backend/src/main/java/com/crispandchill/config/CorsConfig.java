package com.crispandchill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS Configuration
 * 
 * CORS = Cross-Origin Resource Sharing
 * 
 * Why needed?
 * - Frontend runs on http://localhost:5500 (or file://)
 * - Backend runs on http://localhost:8080
 * - Different origins = browser blocks requests by default
 * - CORS configuration allows frontend to access backend
 * 
 * @Configuration marks this as a configuration class
 * @Bean creates a Spring-managed bean
 */
@Configuration
public class CorsConfig {
    
    /**
     * Configure CORS filter
     * 
     * Allows:
     * - All origins (*) - in production, specify exact frontend URL
     * - All HTTP methods (GET, POST, PUT, DELETE, etc.)
     * - All headers
     * - Credentials (cookies, authorization headers)
     * 
     * @return CorsFilter bean
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow credentials (cookies, authorization headers)
        config.setAllowCredentials(true);
        
        // Allow all origins (in production, specify exact URLs)
        // Example for production: config.addAllowedOrigin("https://yourfrontend.com");
        config.addAllowedOriginPattern("*");
        
        // Allow all headers
        config.addAllowedHeader("*");
        
        // Allow all HTTP methods
        config.addAllowedMethod("*");
        
        // Apply configuration to all endpoints
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
