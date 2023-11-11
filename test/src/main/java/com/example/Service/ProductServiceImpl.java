/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Service;

import com.example.Entity.Product;
import com.example.Repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguye
 */
@Service
public class ProductServiceImpl {
    @Autowired
    ProductRepository prepo;

  
    public <S extends Product> S save(S entity) {
        return prepo.save(entity);
    }

    public Optional<Product> findById(Long id) {
        return prepo.findById(id);
    }

    public void deleteById(Long id) {
        prepo.deleteById(id);
    }
    
    

    
    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return prepo.findByPnameContainingIgnoreCase(keyword, pageable);
    }

    public Page<Product> findall(Integer page,Integer page_size,String keyword) {
        Pageable pageable = PageRequest.of(page,page_size);
        return prepo.findByPnameContainingIgnoreCase(keyword, pageable);
    }

    public Page<Product> findallby(String keyword, Long cateid, String rangeprice, Integer page,Integer page_size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "pdate");
        Pageable pageable = PageRequest.of(page,page_size,sort);
        
        return prepo.findallby(keyword, cateid, rangeprice, pageable);
    }
    
}
