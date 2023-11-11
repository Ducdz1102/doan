/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Repository;

import com.example.Entity.OrderDetailDto;
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
public interface OrderDetailDtoRepository extends JpaRepository<OrderDetailDto, String> {
    @Query(value ="SELECT p.pid, p.pname, SUM(t.quantity) AS totalquantity FROM order_detail t "
        + "INNER JOIN product p ON p.pid = t.pid "
        + "GROUP BY p.pid, p.pname", nativeQuery  = true)
    Page<OrderDetailDto> selling( Pageable pageable);
    
    
}

