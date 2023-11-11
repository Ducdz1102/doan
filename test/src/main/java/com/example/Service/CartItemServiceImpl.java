/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Service;

import com.example.Entity.Cart;
import com.example.Entity.CartItem;
import com.example.Entity.Product;
import com.example.Repository.CartItemRepository;
import com.example.Repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguye
 */
@Service
public class CartItemServiceImpl {

    @Autowired
    private CartItemRepository cartItemrepo;
    @Autowired
    private ProductRepository prorepo;

    public CartItem createCartItem(Long productId, Cart cart) {
    Optional<Product> productOptional = prorepo.findById(productId);
    if (productOptional.isPresent()) {
        Product product = productOptional.get();
        CartItem cartItem = new CartItem();
        cartItem.setPid(product);
        cartItem.setQuantity(1);
        cartItem.setPrice(product.getPrice());
        cartItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(1)));
        cartItem.setCartId(cart);
        return cartItemrepo.save(cartItem);
    }
    return null; // Hoặc xử lý lỗi khác tùy theo trường hợp
}

    public void updateQuantity(Long cartItemId, int newQuantity) {
        CartItem cartItem = cartItemrepo.findById(cartItemId).orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(newQuantity);
            cartItem.setTotalPrice(cartItem.getPid().getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            cartItemrepo.save(cartItem);
        }
    }
     public void updateCartItem(CartItem cartItem) {
        cartItemrepo.save(cartItem); // Cập nhật cart item vào cơ sở dữ liệu
    }

    public Optional<CartItem> findById(Long id) {
        return cartItemrepo.findById(id);
    }

    public void deleteById(Long id) {
        cartItemrepo.deleteById(id);
    }

    public void clearCartItems(Cart cart) {
        cartItemrepo.deleteByCartId(cart);
    }
    
    public List<CartItem> getCartItemByCart(Cart cart){
        return cartItemrepo.findByCartId(cart);
    }

    public CartItem findByPid(Product product) {
        return cartItemrepo.findByPid(product);
    }
    

    
    
}
