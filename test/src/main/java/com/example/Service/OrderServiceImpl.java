/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Service;

import com.example.Entity.Orders;
import com.example.Repository.OrderRepository;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguye
 */
@Service
public class OrderServiceImpl {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartServiceImpl cartServiceImpl;
    

    public Page<Orders> findAll(Integer page,Integer page_size) {
        Pageable pageable = PageRequest.of(page,page_size);
        return orderRepository.findAll(pageable);
    }

    public <S extends Orders> S save(S entity) {
        entity.setCreateDate(new Date());
        entity.setStatus("new");
        return orderRepository.save(entity);
    }

    public Optional<Orders> findById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
    
//    public void createOrder(Orders order,Users users){
//        Cart  cart = cartServiceImpl.getcart(users);
//        
//        order.setUid(users);
//        order.setTotalPrice(cart.getTotalPrice());
//        orderRepository.save(order);
//    }

    public Page<Orders> findall(String search, Integer page,Integer page_size) {
        Pageable pageable = PageRequest.of(page,page_size);
        return orderRepository.findall(search, pageable);
    }
    
}
