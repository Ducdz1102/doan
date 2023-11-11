/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example;

import com.example.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 *
 * @author nguye
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll() // Cho phép tải các tệp CSS, JS, hình ảnh từ các thư mục tĩnh
                .antMatchers("/signup","/verify").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/home","home1").hasRole("USER") 
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home1")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // URL cho chức năng logout
                .logoutSuccessUrl("/login") // URL chuyển hướng sau khi logout thành công
                .invalidateHttpSession(true) // Xóa thông tin session sau khi logout
                .deleteCookies("JSESSIONID") // Xóa cookie của session
                .and()
                .rememberMe() // Kích hoạt chức năng "Lưu đăng nhập"
                .rememberMeParameter("remember-me") // Thay đổi tên tham số nếu cần
                .userDetailsService(userService)
                .tokenValiditySeconds(604800) // Thời gian hiệu lực của token (ví dụ: 604800 giây tương ứng với 7 ngày)
                .key("yourSecretKey") // Thay đổi khoá bí mật nếu cần
            .and()

                .csrf().disable();
                
//                .sessionManagement()
//                .invalidSessionUrl("/login?expired") // URL chuyển hướng khi phiên làm việc hết hạn
//                .maximumSessions(1) // Giới hạn số lượng phiên làm việc cho một người dùng
//                .maxSessionsPreventsLogin(false); // Cho phép đăng nhập từ nhiều thiết bị/cửa sổ trình duyệt
//                
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
