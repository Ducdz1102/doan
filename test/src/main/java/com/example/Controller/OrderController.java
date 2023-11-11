/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controller;

import com.example.Entity.Orders;
import com.example.Service.EmailService;
import com.example.Service.OrderDetailServiceImpl;
import com.example.Service.OrderServiceImpl;
import com.example.Service.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nguye
 */
@Controller
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;
    @Autowired
    private OrderDetailServiceImpl orderdetailService;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private EmailService emailService;

    private static String successUrl = "http://localhost:8080/success";
    private static String cancelUrl = "http://localhost:8080/cancel";
//    @GetMapping("/order")
//    public String getOrder(Model model){
//        Orders order = new Orders();
//        model.addAttribute("order",order);
//        return "order";
//    }

    @PostMapping("/order/create")
    public String createOrder(@ModelAttribute("order") Orders order, Model model, HttpSession session) {
        session.setAttribute("pendingOrder", order);
        if (order.getMethodPayment().equals("cash")) {
            orderServiceImpl.save(order);
            orderdetailService.saveall(order.getId());
            emailService.sendmail(order);
        } else if (order.getMethodPayment().equals("online")) {
            try {
                // Sử dụng PayPal API để tạo yêu cầu thanh toán PayPal
                String approvalUrl = payPalService.createPayment(order.getTotalPrice().doubleValue(), "USD", "paypal", "sale", "Thanh toán don hang", cancelUrl, successUrl);
                
                return "redirect:" + approvalUrl;
            } catch (PayPalRESTException e) {
                // Xử lý lỗi nếu có lỗi khi tạo yêu cầu thanh toán
                return "redirect:/error";
            }
        }

        return "redirect:/confirm";

    }

    @GetMapping("/success")
    public String handlePayPalSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model, HttpSession session) {
        Orders order = (Orders) session.getAttribute("pendingOrder");

        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);

            // Thực hiện các hành động "save" và "saveall" sau khi thanh toán thành công
            orderServiceImpl.save(order);
            orderdetailService.saveall(order.getId());
            emailService.sendmail(order);
            // Sau khi xử lý xong, chuyển đến trang "success.html"
            return "redirect:/confirm";
        } catch (PayPalRESTException e) {
            // Xử lý lỗi nếu có lỗi khi xử lý thanh toán
            return "redirect:/error";
        }
    }

    
    @GetMapping("/confirm")
    public String success(HttpSession session,Model model){
        Orders order = (Orders)session.getAttribute("pendingOrder");
        if(order!= null){
            model.addAttribute("order", order);
            return "success";
        }
        else{
            return "redirect:/error";
        }
    }
    
//    @GetMapping("/admin/order")
//    public String findall(
//                @RequestParam(name ="page",defaultValue="0") Integer page,
//                @RequestParam(name ="pagesize",defaultValue="10") Integer page_size,
//                Model model
//            ){
//        model.addAttribute("order", orderServiceImpl.findAll(page, page_size));
//        return "admin/order";
//    }
    
    @GetMapping("/admin/order")
    public String findall(
                @RequestParam(name ="search",required=false) String search,
                @RequestParam(name ="page",defaultValue="0") Integer page,
                @RequestParam(name ="pagesize",defaultValue="10") Integer page_size,
                Model model
            ){
        model.addAttribute("order", orderServiceImpl.findall(search,page, page_size));
        return "admin/order";
    }
}
