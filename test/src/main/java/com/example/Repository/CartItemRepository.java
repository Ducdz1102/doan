/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Repository;

import com.example.Entity.Cart;
import com.example.Entity.CartItem;
import com.example.Entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nguye
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{
    List<CartItem>findByCartId(Cart cart);
    CartItem findByPid(Product product);
    void deleteByCartId(Cart cart);
    
}
