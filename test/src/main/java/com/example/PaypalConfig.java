/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.paypal.base.rest.APIContext;
/**
 *
 * @author nguye
 */
@Configuration
public class PaypalConfig {
    
    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String secretKey;
    
    @Value("${paypal.mode}")
    private String mode;
    
    @Bean
    public APIContext apiContext() {
        APIContext apiContext = new APIContext(clientId, secretKey, mode);
        return apiContext;
    }
}
