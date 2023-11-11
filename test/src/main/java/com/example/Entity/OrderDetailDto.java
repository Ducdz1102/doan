/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nguye
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetailDto {
    @Id
    @Column(name = "pid")
    private Long pid;
    @Column(name = "pname")
    private String pname;
    @Column(name = "totalquantity")
    private int totalquantity;
}
