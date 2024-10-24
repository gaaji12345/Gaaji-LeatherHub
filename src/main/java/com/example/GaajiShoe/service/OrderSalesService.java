package com.example.GaajiShoe.service;/*  gaajiCode
    99
    24/10/2024
    */

import com.example.GaajiShoe.dto.OrdersDTO;

public interface OrderSalesService {

    public String nextCode(String prefix) ;



    public void processOrder(OrdersDTO orderDTO) ;



    public void refundOrder(String orderNo) ;



    public void deleteOrder(String orderNo) ;


    public String getMostSoldItem() ;



    public Double findTotalProfit();

    public String getOrderCount();
}
