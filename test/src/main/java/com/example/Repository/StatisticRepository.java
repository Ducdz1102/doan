/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Repository;

import com.example.Entity.Orders;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nguye
 */
@Repository
public interface StatisticRepository extends JpaRepository<Orders,Long>{
    @Query("select sum(t.totalPrice) from Orders t where (:startdate is null or t.createDate >= convert(Datetime, :startdate,120)) "
            + "and (:enddate is null or t.createDate < convert(datetime, :enddate,120)) ")
    public BigDecimal total(String startdate,String enddate);
    
//    @Query("select t.pid,count(t.quantity) as totalquantity from OrderDetail t "
//            + "inner join Orders o "
//            + "on t.orderid = o.id "
//            + "where (:startdate is null or o.createDate > convert(Datetime, :startdate,120)"
//            + "and (:enddate is null or o.createDate < convert(datetime, :enddate,120) ")
//    public List<OrderDetailDto> selling(String startdate,String enddate);
}
