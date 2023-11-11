/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Repository;

import com.example.Entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nguye
 */
@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT o.* FROM orders o WHERE "
            + ":search IS NULL "
            + "OR TRY_CAST(o.create_date AS DATE) = TRY_CAST(:search AS DATE) "
            + "OR o.status = :search "
            + "or :search = '' ",
            nativeQuery = true,
            countQuery = "SELECT COUNT(*) FROM orders o WHERE "
            + ":search IS NULL "
            + "OR TRY_CAST(o.create_date AS DATE) = TRY_CAST(:search AS DATE) "
            + "OR o.status = :search "
                    + "or :search ='' ")
    Page<Orders> findall(@Param("search") String search, Pageable pageable);

}
