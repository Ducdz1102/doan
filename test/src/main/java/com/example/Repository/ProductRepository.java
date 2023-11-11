/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Repository;

import com.example.Entity.Product;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nguye
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    
    
    Page<Product> findByPnameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM product p WHERE "
            + "(:keyword IS NULL OR p.pname LIKE '%' + :keyword + '%') "
            + "AND (:category IS NULL OR p.cateid = :category) "
            + "AND (:priceRange IS NULL OR :priceRange = '' OR"
            + "  (p.price < 10000000 AND :priceRange = 'under_10m') OR "
            + "  (p.price >= 10000000 AND p.price <= 17000000 AND :priceRange = '10m_to_17m') OR "
            + "  (p.price > 17000000 AND p.price <= 25000000 AND :priceRange = '17m_to_25m') OR "
            + "  (p.price > 25000000 AND :priceRange = 'over_25m'))",
        nativeQuery = true)

    Page<Product> findallby (String keyword, Long category,String priceRange,Pageable pageable);
}
