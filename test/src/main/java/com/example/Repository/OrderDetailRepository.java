/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Repository;

import com.example.Entity.OrderDetail;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 *
 * @author nguye
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
    

}
