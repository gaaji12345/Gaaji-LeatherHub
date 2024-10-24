package com.example.GaajiShoe.repo;/*  gaajiCode
    99
    21/10/2024
    */

import com.example.GaajiShoe.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersDetailsRepo extends JpaRepository<OrderDetails,String> {
    @Query(value = "SELECT SUM(total_price) AS totalSale " +
            "FROM orders " +
            "WHERE MONTH(purchase_date) = MONTH(CURDATE()) AND YEAR(purchase_date) = YEAR(CURDATE())",
            nativeQuery = true)
    Double findTotalSales();

//    @Query(value = "SELECT SUM(sd.unit_price_sale * sd.quantity) - SUM(i.unit_price_buy * sd.quantity) AS totalProfit " +
//            "FROM order_details sd " +
//            "JOIN inventory i ON sd.item_code = i.item_code " +
//            "JOIN sale s ON sd.order_no = s.order_no " +
//            "WHERE MONTH(s.purchase_date) = MONTH(CURDATE()) AND YEAR(s.purchase_date) = YEAR(CURDATE())",
//            nativeQuery = true)
//    Double findTotalProfit();

    void deleteByOrderNo(String orderNo);
    List<OrderDetails> findByOrderNo(String orderNo);

    @Query("SELECT detail.item_code, SUM(detail.quantity) AS totalQuantity " +
            "FROM OrderDetails detail " +
            "GROUP BY detail.item_code " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findMostSoldItems();


    @Query("SELECT SUM((detail.unitPriceSale - detail.unitPriceBuy) * detail.quantity) FROM OrderDetails detail")
    Double findTotalProfit();

}
