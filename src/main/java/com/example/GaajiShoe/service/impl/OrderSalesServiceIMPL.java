package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    21/10/2024
    */

import com.example.GaajiShoe.dto.OrderDetailsDTO;
import com.example.GaajiShoe.dto.OrdersDTO;
import com.example.GaajiShoe.entity.Customer;
import com.example.GaajiShoe.entity.Inventory;
import com.example.GaajiShoe.entity.OrderDetails;
import com.example.GaajiShoe.entity.Orders;
import com.example.GaajiShoe.repo.CustomerRepo;
import com.example.GaajiShoe.repo.InventoryRepo;
import com.example.GaajiShoe.repo.OrderRepo;
import com.example.GaajiShoe.repo.OrdersDetailsRepo;
import com.example.GaajiShoe.service.OrderSalesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderSalesServiceIMPL implements OrderSalesService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrdersDetailsRepo ordersDetailsRepo;

   @Autowired
    CustomerRepo customerRepo;
   @Autowired
    InventoryRepo inventoryRepo;

   @Autowired
    ModelMapper mapper;



    public String nextCode(String prefix) {
        // Get the count of inventory items
        long count = orderRepo.count(); // This gets the total number of rows

        // Generate the next inventory code
        String nextInventoryCode = prefix + String.format("%03d", count + 1);
        return nextInventoryCode;
    }

    @Transactional
    public void processOrder(OrdersDTO orderDTO) {
        // Check if inventory exists
        for (OrderDetailsDTO detail : orderDTO.getSaleDetails()) {
            orderDTO.setOrderNo(nextCode("OO-")); // Generate a new code with the prefix "IIM"

            Optional<Inventory> inventoryOpt = inventoryRepo.findById(detail.getItemCode());

            if (!inventoryOpt.isPresent()) {
                throw new RuntimeException("Item not found in inventory: " + detail.getItemCode());
            }

            Inventory inventory = inventoryOpt.get();

            // Check quantity
            if (inventory.getQuantity() < detail.getQuantity()) {
                throw new RuntimeException("Insufficient stock for item: " + detail.getItemCode());
            }

            // Update inventory quantity
            inventory.setQuantity(inventory.getQuantity() - detail.getQuantity());
            inventoryRepo.save(inventory);
        }

        Customer customer = customerRepo.findById(orderDTO.getCustomerCode())
                .orElseThrow(() -> new RuntimeException("Customer not found with code: " + orderDTO.getCustomerCode()));
        // Create and save order
        Orders order = new Orders();
        order.setOrderNo(orderDTO.getOrderNo());
        order.setCustomerCode(customer);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setPurchaseDate(orderDTO.getPurchaseDate());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setAddPoints(orderDTO.getAddPoints());
        order.setCashierName(orderDTO.getCashierName());

        // Save order
        orderRepo.save(order);

        // Save order details
        for (OrderDetailsDTO detail : orderDTO.getSaleDetails()) {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setOrderNo(orderDTO.getOrderNo());
            orderDetail.setItem_code(detail.getItemCode());
            orderDetail.setUnitPriceBuy(detail.getUnitPriceBuy());
            orderDetail.setUnitPriceSale(detail.getUnitPriceSale());
            orderDetail.setQuantity(detail.getQuantity());
            orderDetail.setSale(order); // Set the relation

            ordersDetailsRepo.save(orderDetail);
        }
    }

    @Transactional
    public void refundOrder(String orderNo) {
        // Fetch the order to be refunded
        Orders order = orderRepo.findById(orderNo)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderNo));

        // Reverse inventory updates
        List<OrderDetails> orderDetails = ordersDetailsRepo.findByOrderNo(orderNo);
        for (OrderDetails detail : orderDetails) {
            Optional<Inventory> inventoryOpt = inventoryRepo.findById(detail.getItem_code());

            if (inventoryOpt.isPresent()) {
                Inventory inventory = inventoryOpt.get();
                // Increase inventory quantity
                inventory.setQuantity(inventory.getQuantity() + detail.getQuantity());
                inventoryRepo.save(inventory);
            }
        }

        // Optionally, handle customer points or payment adjustments here

        // Delete order details
        ordersDetailsRepo.deleteAll(orderDetails);

        // Delete the order
        orderRepo.delete(order);
    }


    @Transactional
    public void deleteOrder(String orderNo) {
        // Fetch the order to be deleted
        Orders order = orderRepo.findById(orderNo)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderNo));

        // Fetch and delete order details
        List<OrderDetails> orderDetails = ordersDetailsRepo.findByOrderNo(orderNo);
        ordersDetailsRepo.deleteAll(orderDetails);

        // Delete the order
        orderRepo.delete(order);
    }

    public String getMostSoldItem() {
        List<Object[]> results = ordersDetailsRepo.findMostSoldItems();

        if (!results.isEmpty()) {
            // Get the most sold item code
            Object[] mostSold = results.get(0);
            return (String) mostSold[0]; // Assuming item_code is the first element
        }

        return null; // or throw an exception if needed
    }


    public Double findTotalProfit() {
        return ordersDetailsRepo.findTotalProfit();
    }

    public String getOrderCount() {
        return orderRepo.countTotalOrders();
    }
}
