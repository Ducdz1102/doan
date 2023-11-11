/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Service;

import com.example.Entity.Category;
import com.example.Repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguye
 */
@Service
public class CategoryServiceImpl {
    @Autowired
    CategoryRepository crepo;

    public <S extends Category> S save(S entity) {
        return crepo.save(entity);
    }
    
    public Category findById(Long id) {
        return crepo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        crepo.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return crepo.findAll();
    }

    public Page<Category> findAll(String keyword,Integer page,Integer page_size) {
        
        Pageable pageable = PageRequest.of(page,page_size);
        return crepo.findByCnameContainingIgnoreCase(keyword,pageable);
    }
}
