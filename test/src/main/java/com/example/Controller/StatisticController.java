/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controller;


import com.example.Entity.OrderDetailDto;
import com.example.Service.StatisticsServiceImpl;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nguye
 */
@Controller
public class StatisticController {
    @Autowired
    private StatisticsServiceImpl statisticsServiceImpl;
    
    
    @GetMapping("/admin/revenue")
    public String total(@RequestParam(name ="startdate",defaultValue = "") String startdate,
                            @RequestParam(name ="enddate",defaultValue = "") String enddate,
                            Model model
            ){
        BigDecimal total = statisticsServiceImpl.total(startdate, enddate);
        model.addAttribute("total",total);
        return "admin/statistics";
    }
    
    @GetMapping("/admin/totalsell")
    public String totalsell(
            
            @RequestParam(name = "page",defaultValue ="0") Integer page,
            @RequestParam(name ="pageSize",defaultValue = "3") Integer pageSize,
            Model model
    ){
        Page<OrderDetailDto> totalsell = statisticsServiceImpl.totalsell( page, pageSize);
        model.addAttribute("page",totalsell);
        return "admin/totalsell";
    }
}
