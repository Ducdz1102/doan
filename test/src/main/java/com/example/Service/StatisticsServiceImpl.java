/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Service;


import com.example.Entity.OrderDetailDto;
import com.example.Repository.OrderDetailDtoRepository;
import com.example.Repository.StatisticRepository;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguye
 */
@Service
public class StatisticsServiceImpl {
    @Autowired
    private StatisticRepository statisticRepository;
    @Autowired
    private OrderDetailDtoRepository oddr;
    
    public BigDecimal total (String startdate,String enddate){
        
        if(startdate.isEmpty()){
            startdate = LocalDate.now().withDayOfMonth(1).toString();
        }
        if(enddate.isEmpty()){
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        enddate = dateFormat.format(new Date());
        }
        
        return statisticRepository.total(startdate, enddate);
    }
    
    public Page<OrderDetailDto> totalsell(Integer page,Integer pageSize){
        Sort sort=Sort.by(Sort.Direction.DESC,"totalquantity");
        Pageable pageable =PageRequest.of(page,pageSize,sort);
        
        return oddr.selling( pageable);
          
    }
    
}
