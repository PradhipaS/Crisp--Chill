package com.crispandchill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class
 * 
 * This is the entry point of the Spring Boot application.
 * 
 * @SpringBootApplication annotation combines:
 * - @Configuration: Marks this as a configuration class
 * - @EnableAutoConfiguration: Enables Spring Boot's auto-configuration
 * - @ComponentScan: Scans for components in this package and sub-packages
 * 
 * When you run this class, Spring Boot will:
 * 1. Start an embedded Tomcat server (default port 8080)
 * 2. Scan for @Controller, @Service, @Repository classes
 * 3. Connect to MySQL database using application.properties
 * 4. Create REST API endpoints
 */
@SpringBootApplication
public class BackendApplication {

    /**
     * Main method - starts the application
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("🚀 Crisp & Chill Backend Started!");
        System.out.println("📍 Server running at: http://localhost:8080");
        System.out.println("📊 Database: crisp_chill_db");
        System.out.println("========================================\n");
    }
}
