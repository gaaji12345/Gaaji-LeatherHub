package com.example.GaajiShoe.repo;/*  gaajiCode
    99
    21/10/2024
    */

import com.example.GaajiShoe.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepo extends JpaRepository<Orders,String> {

    @Query(value = "SELECT order_no FROM Orders ORDER BY order_no DESC LIMIT 1", nativeQuery = true)
    String getLastIndex();

    @Query(value = "SELECT COUNT(order_no) FROM Orders", nativeQuery = true)
    int getSumOrders();

    @Query(value = "SELECT item_code FROM order_details GROUP BY item_code ORDER BY quantity DESC LIMIT 1", nativeQuery = true)
    Object[] getMostSoldItem();

    @Query(value = "SELECT item_pic FROM inventory INNER JOIN order_details ON inventory.item_code = order_details.item_code GROUP BY OrderDetails.item_code ORDER BY SUM(OrderDetails.quantity) DESC LIMIT 1", nativeQuery = true)
    String getBase64EncodedImageOfMostSoldItem();

    @Query(value = "SELECT SUM(quantity) AS total_quantity FROM order_details GROUP BY item_code ORDER BY total_quantity DESC LIMIT 1", nativeQuery = true)
    int getMostSoldItemQuantity();

    @Query(value = "SELECT order_no FROM orders ORDER BY order_no DESC LIMIT 1", nativeQuery = true)
    String findLatestOrderCode();

    @Query("SELECT COUNT(o) FROM Orders o")
   String countTotalOrders();
}
