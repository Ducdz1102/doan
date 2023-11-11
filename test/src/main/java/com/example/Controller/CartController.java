package com.example.Controller;

import com.example.Entity.Cart;
import com.example.Entity.CartItem;
import com.example.Entity.Orders;
import com.example.Entity.Users;
import com.example.Service.CartItemServiceImpl;
import com.example.Service.CartServiceImpl;
import com.example.Service.ProductServiceImpl;
import com.example.Service.UserServiceImpl;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {

    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private CartItemServiceImpl cartItemService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/cart")
    public String viewCart(@RequestParam("cartId") Long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "cartError";
        }

        List<CartItem> cartItems = cartItemService.getCartItemByCart(cart);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cartItems);

        return "cart1";
    }

    @PostMapping("/cart/addItem")
    public String addToCart(@RequestParam("productId") Long productId, 
            @AuthenticationPrincipal UserDetails userDetails) {
        Users currentUser = userService.findByUsername(userDetails.getUsername());
        cartService.addToCart(productId, currentUser);
        Cart cart=cartService.getcart(currentUser);

        return "redirect:/cart?cartId=" + cart.getId();
    }

    @PostMapping("/cart/updateQuantity")
    @ResponseBody
    public Map<String, Object> updateCartItemQuantity(@RequestParam("cartItemId") Long cartItemId,
                                       @RequestParam("cartId") Long cartId,
                                       @RequestParam("quantity") int newQuantity) {
        // Cập nhật số lượng sản phẩm của cart item
        cartService.updateCartItemQuantity(cartItemId, newQuantity);

        BigDecimal newTotalPrice = cartItemService.findById(cartItemId).get().getTotalPrice();
        BigDecimal TotalPrice = cartService.getCartById(cartId).getTotalPrice();
        Map<String, Object> response = new HashMap<>();
        response.put("newTotalPrice", newTotalPrice);
        response.put("TotalPrice",TotalPrice);
        return response;
    }
    
    @PostMapping("/cart/removeItem")
    public String removeFromCart(@RequestParam("cartItemId") Long cartItemId, @RequestParam("cartId") Long cartId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart?cartId=" + cartId;
    }

//    @PostMapping("/cart/clear")
//    public String clearCart(@RequestParam("cartId") Long cartId) {
//        cartService.clearCart(cartId);
//        return "redirect:/cart?cartId=" + cartId;
//    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam("cartId") Long cartId, Model model,Principal principal) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "cartError";
        }
        Users currentUser = userService.findByUsername(principal.getName());
        Orders order = new Orders();
        order.setUid(currentUser);
        order.setTotalPrice(cart.getTotalPrice());
 
        model.addAttribute("order",order);
        model.addAttribute("totalPrice",cart.getTotalPrice());
        
        return "order";
    }
    
}
