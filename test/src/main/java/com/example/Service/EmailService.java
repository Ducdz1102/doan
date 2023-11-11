/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Service;

import com.example.Entity.Orders;
import com.example.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    
    public void sendmail(Orders order) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        
        try {
            helper.setTo(order.getUid().getEmail());
            helper.setSubject("Xác nhận đơn hàng #" + order.getId());
            
            // Thiết lập nội dung HTML của email
            String htmlContent = "<html><body>"
                    + "<h1>Cảm ơn bạn đã đặt hàng!</h1>"
                    + "<p>Đơn hàng của bạn đã được xác nhận.</p>"
                    + "</body></html>";
            
            helper.setText(htmlContent, true);
            
            emailSender.send(message);
        } catch (MessagingException e) {
            // Xử lý ngoại lệ nếu có lỗi khi gửi email
            e.printStackTrace();
        }
    }
    
    public void sendVerificationEmail(String to, String verificationCode) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setSubject("Xác minh email");
            helper.setTo(to);
            // Đặt nội dung email dưới dạng HTML
            String htmlContent = "<html><body><p>Mã xác minh của bạn: " + verificationCode + "</p></body></html>";
            helper.setText(htmlContent, true);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Xử lý lỗi khi gửi email
        }
    }
}

