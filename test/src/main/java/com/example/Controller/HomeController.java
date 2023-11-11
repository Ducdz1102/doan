/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controller;

import com.example.Entity.Cart;
import com.example.Entity.Category;
import com.example.Entity.Product;
import com.example.Entity.Users;
import com.example.Service.CartServiceImpl;
import com.example.Service.CategoryServiceImpl;
import com.example.Service.ProductServiceImpl;
import com.example.Service.UserServiceImpl;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nguye
 */
@Controller
public class HomeController {

    @Autowired
    ProductServiceImpl prod;
    @Autowired
    CategoryServiceImpl cate;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CartServiceImpl cartService;
  
//    @GetMapping("/home1")
//    public String home(
//            @RequestParam(name = "page", defaultValue = "0") Integer page,
//            @RequestParam(name ="page_size",defaultValue = "1") Integer page_size,
//            @RequestParam(name = "category", required = false) Long categoryId,
//            @RequestParam(name = "keyword", required = false) String keyword,
//            Model model
//    ) {
//       
//        // Lấy danh sách danh mục để hiển thị trong dropdown
////        Iterable<Category> categories = cate.getAllCategories();
////        model.addAttribute("category", categories);
//        
//        Page<Product> productPage =prod.findAll(categoryId,keyword,page,page_size);
//
//        model.addAttribute("productPage", productPage);
//
//        return "home2";
//    }

   
    
    @GetMapping("/product")
    public String Detail(@RequestParam Long pid,Model model){
        Product product=prod.findById(pid).orElse(null);
        model.addAttribute("product",product);
        return "product";
    }
    
//    @GetMapping("/home")
//    public String home2(){
//        return "home";
//    }
    
    @ModelAttribute("category")
    public  Iterable<Category> getcategory(){
        Iterable<Category> categories = cate.getAllCategories();
        return categories;
    }
    @ModelAttribute("cart")
    public Cart getCart(@AuthenticationPrincipal UserDetails userDetails){
        Users currentUser = userService.findByUsername(userDetails.getUsername());
        Cart cart=cartService.getcart(currentUser);
        if(cart == null){
            cartService.createCart(currentUser);
            cart=cartService.getcart(currentUser);
        }
        return cart;
    }
    @GetMapping("/cart1")
    public String abc(){
        return "cart1";
    }
    
    @GetMapping("/home1")
    public String home1(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name ="page_size",defaultValue = "1") Integer page_size,
            @RequestParam(name = "category", defaultValue = "", required = false) Long categoryId,
            @RequestParam(name = "keyword", defaultValue = "", required = false) String keyword,
            @RequestParam(name = "priceRange", defaultValue = "", required = false) String rangeprice,
            Model model
    ) {
 
        Page<Product> productPage =prod.findallby(keyword,categoryId,rangeprice,page,page_size);

        model.addAttribute("productPage", productPage);

        return "home2";
    }
}
