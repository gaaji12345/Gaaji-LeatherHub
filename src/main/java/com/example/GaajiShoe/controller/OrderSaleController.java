package com.example.GaajiShoe.controller;/*  gaajiCode
    99
    21/10/2024
    */

import com.example.GaajiShoe.dto.OrdersDTO;
import com.example.GaajiShoe.service.impl.OrderSalesServiceIMPL;
import com.example.GaajiShoe.util.ResponceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderSaleController {

    @Autowired
    OrderSalesServiceIMPL testNew;






    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseOrder(@RequestBody OrdersDTO orderDTO) {
        testNew.processOrder(orderDTO);
        return ResponseEntity.ok("Order processed successfully");
    }


    @PostMapping("/refund/{orderNo}")
    public ResponseEntity<String> refundOrder(@PathVariable("orderNo") String orderNo) {
        try {
            testNew.refundOrder(orderNo);
            return ResponseEntity.ok("Order refunded successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/generateCode")
    public String generateCode(@RequestParam("prefix") String prefix) {
        return testNew.nextCode(prefix);
    }


    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public  ResponceUtil deleteOrder(@RequestParam("orderNo") String orderNo){
        testNew.deleteOrder(orderNo);
        return new ResponceUtil(200,"Deleted Success Order",null);

    }

    @GetMapping("/most-sold-item")
    public ResponseEntity<String> getMostSoldItem() {
        String mostSoldItem = testNew.getMostSoldItem();

        if (mostSoldItem != null) {
            return ResponseEntity.ok(mostSoldItem);
        } else {
            return ResponseEntity.noContent().build(); // Return 204 if no items sold
        }
    }

    @GetMapping("/total-profit")
    public ResponseEntity<Double> getTotalProfit() {
        Double totalProfit = testNew.findTotalProfit();
        return ResponseEntity.ok(totalProfit);
    }

    @GetMapping("/count")
    public ResponseEntity<String> getOrderCount() {
        String orderCount = testNew.getOrderCount();
        return ResponseEntity.ok(orderCount);
    }


}
