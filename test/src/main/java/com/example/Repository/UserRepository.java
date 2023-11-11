/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Repository;

import com.example.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nguye
 */
public interface UserRepository extends JpaRepository<Users,Long>{
    Users findByUsername(String username);
    Users findByEmail(String email);
    Users findByVerifycode(String verifycode);
}
