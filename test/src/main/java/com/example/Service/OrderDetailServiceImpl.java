/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Service;

import com.example.Entity.Cart;
import com.example.Entity.CartItem;
import com.example.Entity.OrderDetail;
import com.example.Entity.Orders;
import com.example.Repository.OrderDetailRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nguye
 */
@Service
public class OrderDetailServiceImpl {
    @Autowired
    private OrderDetailRepository detailRepository;
    @Autowired
    private CartItemServiceImpl cartItemServiceImpl;
    @Autowired
    private OrderServiceImpl OrderServiceImpl;
    
    @Autowired
    private CartServiceImpl cartServiceImpl;
    
    public Page<OrderDetail> findAll(Pageable pageable) {
        return detailRepository.findAll(pageable);
    }

   

    public Optional<OrderDetail> findById(Long id) {
        return detailRepository.findById(id);
    }

    public void deleteById(Long id) {
        detailRepository.deleteById(id);
    }
    
//    public List<OrderDetail> save(Long productId,Cart cart){
//        List<CartItem> item =cartItemServiceImpl.getCartItemByCart(cart);
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        Product product = productservice.findById(productId).get();
//        for(CartItem cartitem: item){
//            OrderDetail order= new OrderDetail();
//            
//            order.setPid(product);
//            order.setOrderid(orderid);
//            order.setQuantity(cartitem.getQuantity());
//            order.setPrice(cartitem.getPrice());
//            order.setTotalPrice(cartitem.getTotalPrice());
//        }
//        
//    }
    @Transactional
    public void saveall(Long id){
        Orders orders =OrderServiceImpl.findById(id).get();
        Cart cart=cartServiceImpl.getcart(orders.getUid());
        List<CartItem> item = cartItemServiceImpl.getCartItemByCart(cart);
        List<OrderDetail> listorderDetail = new ArrayList();
                item.forEach(
                o-> {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderid(orders);
                    orderDetail.setPid(o.getPid());
                    orderDetail.setQuantity(o.getQuantity());
                    orderDetail.setPrice(o.getPrice());
                    orderDetail.setTotalPrice(o.getTotalPrice());
                    
                    listorderDetail.add(orderDetail);
                }
        );
        detailRepository.saveAll(listorderDetail);
        cartServiceImpl.clearCart(cart);
    }
}
