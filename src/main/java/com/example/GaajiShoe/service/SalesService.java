package com.example.GaajiShoe.service;/*  gaajiCode
    99
    20/10/2024
    */

import com.example.GaajiShoe.dto.SalesDTO;

import java.util.List;
import java.util.Map;

public interface SalesService {
    List<SalesDTO> getAllSales();

    SalesDTO getSaleDetails(String id);
    SalesDTO saveSales(SalesDTO salesDTO);
    void updateSales(String id, SalesDTO salesDTO);
    void deleteSales(String id);
    String nextOrderCode();
    Map<String, Double> getWeeklyProfit();
    Double getMonthlyRevenue();
}
