/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controller;

import com.example.Entity.Users;
import com.example.Service.EmailService;
import com.example.Service.UserServiceImpl;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author nguye
 */
@Controller
public class Login {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    private EmailService emailService;

     @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String processLoginForm(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        Users user = userService.findByUsername(username);
        if (user == null || !userService.isPasswordCorrect(username, password)) {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("user",user);
            return "redirect:/login";
        }

        // Đăng nhập thành công, chuyển hướng người dùng đến trang chính
        return "redirect:/home1";
    }

//
//@PostMapping("/login")
//    public String processLoginForm(@ModelAttribute("user") Users user, Model model) {
//        String username = user.getUsername();
//        String password = user.getPassword();
//
//        // Thực hiện xác thực người dùng
//        UserDetails userDetails = userService.loadUserByUsername(username);
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password);
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//        // Đăng nhập thành công, đặt thông tin xác thực vào SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Đăng nhập thành công, chuyển hướng người dùng đến trang chính
//        return "redirect:/home1";
//    }

//    @GetMapping("/home")
//    public String showHomePage() {
//        return "home";
//    }

    // Nếu đăng xuất, chuyển hướng người dùng đến trang đăng nhập
   


//    @GetMapping("/dashboard")
//    public String dashboard(Model model) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Users user = userService.findByUsername(userDetails.getUsername());
//        model.addAttribute("user", user);
//        return "dashboard";
//    }

//    @ExceptionHandler(Exception.class)
//    public String handleException() {
//        return "error";
//    }
    

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("user", new Users());
        return "signup";
    }

    @PostMapping("/signup")
    public String processRegistration(@ModelAttribute("user") Users user, Model model) {
        Users existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("error", "Username already exists");
            return "signup";
        }
        String verificationCode = UUID.randomUUID().toString();
        user.setVerifycode(verificationCode);
        user.setIsverified("disable");
        
        userService.createUser(user);
        emailService.sendVerificationEmail(user.getEmail(),user.getVerifycode());
        return "redirect:/verify";
    }
   
    
    @GetMapping("/verify")
    public String showVerificationForm(Model model) {
        model.addAttribute("verificationCode", "");
        return "verify";
    }
    
    @PostMapping("/verify")
    public String verifyEmail(@RequestParam("verificationCode") String verificationCode) {
        Users user = userService.findByVerificationCode(verificationCode);
        if (user != null) {
            user.setIsverified("enable");
            userService.save(user);
            return "redirect:/login";
        } else {
            return "redirect:/error";
        }
    }
    
//    @RequestMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        // Thực hiện logout bằng SecurityContextLogoutHandler
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//
//        // Chuyển hướng về trang đăng nhập sau khi logout thành công
//        return "redirect:/login?logout";
//    }


}
