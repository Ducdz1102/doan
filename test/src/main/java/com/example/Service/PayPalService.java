/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Service;

/**
 *
 * @author nguye
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayPalService {

    private final APIContext apiContext;
    
    
    @Autowired
    public PayPalService(APIContext apiContext) {
        this.apiContext = apiContext;
    }
    
    @Transactional
    public String createPayment(double amount, String currency, String method, String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount paymentAmount = new Amount(currency, String.format("%.2f", amount));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(paymentAmount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment(intent, payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);
        for (Links links : createdPayment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                return links.getHref();
            }
        }

        return null;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        Payment payment = new Payment();
        payment.setId(paymentId);
        return payment.execute(apiContext, paymentExecution);
    }
}
