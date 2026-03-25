package com.crispandchill.repository;

import com.crispandchill.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * CategoryRepository - Database access for Category entity
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    /**
     * Find category by name
     * 
     * @param categoryName category name
     * @return Optional<Category>
     */
    Optional<Category> findByCategoryName(String categoryName);
    
    /**
     * Check if category exists by name
     * 
     * @param categoryName category name
     * @return true if exists
     */
    boolean existsByCategoryName(String categoryName);
}
