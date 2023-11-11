
package com.example.Controller;
import com.example.Service.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaypalController {

    private final PayPalService payPalService;
    private static String successUrl ="http://localhost:8080/success";
    private static String cancelUrl ="http://localhost:8080/cancel";
    @Autowired
    public PaypalController(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

//    @GetMapping("/checkout")
//    public String checkout() {
//        return "checkout";
//    }
//
//    @PostMapping("/process-payment")
//    public String processPayment(Model model) {
//        try {
//            String approvalUrl = payPalService.createPayment(50.0, "USD", "paypal", "sale", "Thanh toán sản phẩm XYZ",cancelUrl, successUrl);
//            return "redirect:" + approvalUrl;
//        } catch (PayPalRESTException e) {
//            // Xử lý lỗi
//            return "redirect:/error";
//        }
//    }

    @GetMapping("/cancel")
    public String cancel() {
        return "cancelError";
    }

//    @GetMapping("/success")
//    public String success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model) {
//        try {
//            Payment payment = payPalService.executePayment(paymentId, payerId);
//            // Xử lý kết quả thành công
//            return "redirect:/home1";
//        } catch (PayPalRESTException e) {
//            // Xử lý lỗi
//            return "redirect:/cancel";
//        }
//    }

    @GetMapping("/paypal")
    public String error() {
        return "paypal";
    }
}
