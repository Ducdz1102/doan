package com.example.Service;

import com.example.Entity.Cart;
import com.example.Entity.CartItem;
import com.example.Entity.Product;
import com.example.Entity.Users;
import com.example.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemServiceImpl cartItemService;
    @Autowired
    private ProductServiceImpl productservice;

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    public void updateCart(Cart cart) {
        cartRepository.save(cart); // Lưu thông tin giỏ hàng vào cơ sở dữ liệu
    }
    
    public void createCart(Users user){
        Cart cart = new Cart();
        cart.setUid(user);
        cart.setTotalItems(0);
        cart.setTotalPrice(BigDecimal.ZERO);
        cart.setCreatedAt(new Date());
        cartRepository.save(cart);
   }
    
    @Transactional
public void addToCart(Long productId, Users uid) {
    // Tìm giỏ hàng của người dùng
    Product product = productservice.findById(productId).get();
    Cart cart = cartRepository.findByUid(uid);
    
    if (cart == null) {
        createCart(uid);
    }
    
    List<CartItem> existingCartItem = cartItemService.getCartItemByCart(cart);
    boolean productExistsInCart = false;

    if (existingCartItem != null) {
        for (CartItem item : existingCartItem) {
            if (item.getPid().getPid() == productId) {
                item.setQuantity(item.getQuantity() + 1);
                item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                cartItemService.updateCartItem(item);
                productExistsInCart = true;
            }
        }
    }

    if (!productExistsInCart) {
        // Nếu sản phẩm chưa có trong giỏ hàng, tạo một mặt hàng mới
        cartItemService.createCartItem(productId,cart);
    }
    // Cập nhật giỏ hàng trong cơ sở dữ liệu
        updateCartTotal(cart);
}

    @Transactional
    public void removeFromCart(Long cartItemId) {
        
        CartItem cartItem = cartItemService.findById(cartItemId).orElse(null);
        if (cartItem != null) {
            Cart cart = cartItem.getCartId(); 
            cartItemService.deleteById(cartItemId);
            updateCartTotal(cart);
        }
    }

    public void clearCart(Cart cart) {
        cartItemService.clearCartItems(cart);
        
        updateCartTotal(cart);
    }

    @Transactional
    private void updateCartTotal(Cart cart) {
        List<CartItem> item = cartItemService.getCartItemByCart(cart);
        int totalItems = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        if(item!=null){
            for (CartItem cartItem : item) {
            totalItems ++;
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);
        }
        updateCart(cart);
    }

    public void updateCartItemQuantity(Long cartItemId, int newQuantity) {
        CartItem cartItem = cartItemService.findById(cartItemId).orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(newQuantity);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemService.updateCartItem(cartItem);
            Cart cart = cartItem.getCartId();
            updateCartTotal(cart);
        }
    }
    
    public Cart getcart(Users u){
        return cartRepository.findByUid(u);
    }
}
