/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Service;

import com.example.Entity.Authorities;
import com.example.Entity.Users;
import com.example.Repository.AuthoritiesRepository;
import com.example.Repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguye
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userrepo;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userrepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if(user.getIsverified()=="disable"){
            throw new DisabledException("Email is not verified");
        }
        
        List<Authorities> authorities = authoritiesRepository.findByUserId(user);
        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPass(), grantedAuthorities);
    }

    public Users findByUsername(String username) {
        return userrepo.findByUsername(username);
    }

    public boolean isPasswordCorrect(String username, String password) {
        Users user = findByUsername(username);
        if (user != null) {
            return password().matches(password, user.getPass());
        }
        return false;
    }

    public void createUser(Users user) {
        String encodedPassword = password().encode(user.getPass());
        user.setPass(encodedPassword);
        userrepo.save(user);
        Authorities authorities = new Authorities();
        authorities.setUserId(user);
        authorities.setAuthority("ROLE_ADMIN");
        authoritiesRepository.save(authorities);
    }

    public <S extends Users> S save(S entity) {
        return userrepo.save(entity);
    }

    public Users findByVerificationCode(String findByVerificationCode){
        return userrepo.findByVerifycode(findByVerificationCode);
    }
    
    @Bean
    public PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    
}
