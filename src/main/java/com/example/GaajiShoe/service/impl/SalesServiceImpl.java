package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    20/10/2024
    */

import com.example.GaajiShoe.dto.InventoryDTO;
import com.example.GaajiShoe.dto.SalesDTO;
import com.example.GaajiShoe.dto.SlaesInventoryDTO;
import com.example.GaajiShoe.entity.Inventory;
import com.example.GaajiShoe.entity.Sales;
import com.example.GaajiShoe.entity.SalesDetails;
import com.example.GaajiShoe.repo.InventoryRepo;
import com.example.GaajiShoe.repo.SalesDetailsRepo;
import com.example.GaajiShoe.repo.SalesRepo;
import com.example.GaajiShoe.service.SalesService;
import com.example.GaajiShoe.util.exeption.NotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SalesServiceImpl implements SalesService {

    @Autowired
    SalesRepo salesRepo;
    @Autowired
    SalesDetailsRepo detailsRepo;
    @Autowired
    InventoryRepo inventoryRepo;
    @Autowired
    ModelMapper mapper;


    @Override
    public List<SalesDTO> getAllSales() {
        List<Sales> salesList = salesRepo.findAll();
        return salesList.stream().map(sales -> {
            SalesDTO salesDTO = mapper.map(sales, SalesDTO.class);

            List<SalesDetails> salesDetailsList = detailsRepo.findAllBySalesOrderNo(sales.getOrderNo());
            List<SlaesInventoryDTO> salesInventoryDTOList = salesDetailsList.stream()
                    .map(details -> {
                        SlaesInventoryDTO salesInventoryDTO = mapper.map(details, SlaesInventoryDTO.class);
                        salesInventoryDTO.setInventory(mapper.map(details.getInventory(), InventoryDTO.class));
                        return salesInventoryDTO;
                    })
                    .collect(Collectors.toList());

            salesDTO.setInventory(salesInventoryDTOList);
            return salesDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public SalesDTO getSaleDetails(String id) {
        if(!salesRepo.existsByOrderNo(id)){
            throw new NotFoundException("Sales "+id+" Not Found!");
        }
        SalesDTO salesDTO = mapper.map(salesRepo.findByOrderNo(id), SalesDTO.class);
        System.out.println("ID-----------------------"+id);
        List<SlaesInventoryDTO> salesInventory = detailsRepo.findAllBySalesOrderNo(id).stream().map(
                salesDetails -> mapper.map(salesDetails, SlaesInventoryDTO.class)
        ).toList();
        salesDTO.setInventory(salesInventory);

        return salesDTO;
    }

    @Transactional
    @Override
    public SalesDTO saveSales(SalesDTO salesDTO) {
        if(maintainInventoryQuantity(salesDTO)){
            if(salesRepo.existsByOrderNo(salesDTO.getOrderNo())){
                throw new NotFoundException("This Sales "+salesDTO.getOrderNo()+" already exicts...");
            }
            SalesDTO newsalesDTO = mapper.map(salesRepo.save(mapper.map(
                    salesDTO, Sales.class)), SalesDTO.class
            );

            List<SlaesInventoryDTO> salesInventoryDTO = new ArrayList<>();
            for (SlaesInventoryDTO inventoryDTO : salesDTO.getInventory()) {
                SalesDetails savedSaleDetails = detailsRepo.save(mapper.map(inventoryDTO, SalesDetails.class));
                salesInventoryDTO.add(mapper.map(savedSaleDetails, SlaesInventoryDTO.class));
            }
            newsalesDTO.setInventory(salesInventoryDTO);
            return newsalesDTO;
        }else {
            return salesDTO;
        }
    }

    @Transactional
    @Override
    public void updateSales(String id, SalesDTO salesDTO) {

        for(SlaesInventoryDTO inventoryDTO : salesDTO.getInventory()){
            if(inventoryDTO.getQuantity() == 0){
                if(!isDateWithinThreeDays(String.valueOf(salesDTO.getPurchaseDate()))){
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("comming");
                    throw new NotFoundException("Update Failed This Order " +
                            salesDTO.getOrderNo() + " Can't refund");
                }
            }
        }
        if(salesRepo.existsById(salesDTO.getOrderNo())){
            salesRepo.save(mapper.map(salesDTO,Sales.class));
            for (SlaesInventoryDTO inventoryDTO : salesDTO.getInventory()) {
                if(!detailsRepo.existsById(inventoryDTO.getId())){
                    throw new NotFoundException("Update Failed; Sales id: " +
                            salesDTO.getOrderNo() + " does not exist");
                }
                detailsRepo.save(mapper.map(inventoryDTO, SalesDetails.class));
            }
        }
    }

    @Override
    public void deleteSales(String id) {
        if(!detailsRepo.existsBySalesOrderNo(id)&&!salesRepo.existsByOrderNo(id)){
            throw  new NotFoundException("Sales "+ id + "Not Found...");
        }else if(salesRepo.existsByOrderNo(id)){
            salesRepo.deleteByOrderNo(id);
        }
        detailsRepo.deleteAllBySalesOrderNo(id);
        salesRepo.deleteByOrderNo(id);

    }

    @Override
    public String nextOrderCode() {
        String lastOrderCode = salesRepo.findLatestOrderCode();
        if(lastOrderCode==null){lastOrderCode = "ORD0000";}
        int numericPart = Integer.parseInt(lastOrderCode.substring(4));
        numericPart++;
        String nextOrderCode = "ORD" + String.format("%04d", numericPart);
        return nextOrderCode;
    }

    @Override
    public Map<String, Double> getWeeklyProfit() {
        Map<String, Double> dataList = new HashMap<>();
        List<Date> dates = new ArrayList<>();
        int quantity;
        double dayProfit = 0;
        dates = salesRepo.findAllPurchaseDate();
        for (Date date:dates){
            if(convertToLocalDateFormat(String.valueOf(date))){
                List<Sales> sales = salesRepo.findAllByPurchaseDate(date);
                for (int i = 0; i < sales.size(); i++) {
                    List<SalesDetails> salesDetailsArray = detailsRepo.findAllBySalesOrderNo(sales.get(i).getOrderNo());
                    for (int j = 0; j < salesDetailsArray.size(); j++){
                        System.out.println("----------------------------------------------------------------");
                        quantity = salesDetailsArray.get(j).getQuantity();
                        InventoryDTO inventoryDTO = mapper.map(
                                inventoryRepo.findByItemCode(salesDetailsArray.get(j).getInventory().getItemCode()),InventoryDTO.class
                        );
                        dayProfit += quantity*(inventoryDTO.getExpectedProfit());
                    }
                    System.out.println("////////////////////////////////////////////////////////////////");
                    System.out.println(date);
                    System.out.println(dayProfit);
                    if(dayProfit!=0){
                        String newdate = convertDateFormat(String.valueOf(date));
                        if (!dataList.containsKey(newdate)) {
                            dataList.put(newdate, dayProfit);
                        } else {
                            double currentProfit = dataList.get(newdate);
                            dataList.put(newdate, currentProfit + dayProfit);
                        }
                    }
                    dayProfit = 0;
                }
            }
        }
        System.out.println(dataList);
        return dataList;
    }

    @Override
    public Double getMonthlyRevenue() {
        return salesRepo.getCurrentMonthTotalRevenue();
    }
    protected Boolean maintainInventoryQuantity(SalesDTO salesDTO){
        boolean valid = false;
        int quantity;
        for (int i = 0; i<salesDTO.getInventory().size();i++){
            SlaesInventoryDTO inventoryDTO = salesDTO.getInventory().get(i);
            String itemCode = inventoryDTO.getInventory().getItemCode();
            System.out.println(itemCode);

            InventoryDTO inventory = mapper.map(inventoryRepo.findByItemCode(itemCode),InventoryDTO.class);
            if(inventory.getQuantity()>0){
                if(inventory.getQuantity()-inventoryDTO.getQuantity()>=0){
                    quantity = inventory.getQuantity()-inventoryDTO.getQuantity();
                    inventory.setQuantity(quantity);
                    if(quantity>100){
                        inventory.setStatus("available");
                    }else if(quantity<=100){
                        inventory.setStatus("low");
                    }else if(quantity==0){
                        inventory.setStatus("not");
                    }
                    inventoryRepo.save(mapper.map(inventory, Inventory.class));
                    valid = true;
                }else{
                    valid = false;
                    throw new ServiceException("Can't Proceed this Sale ."+inventory.getItemDescription()+" No much Quantity");
                }
            }else{
                valid = false;
                throw new ServiceException("Can't Proceed this Sale ."+inventory.getItemDescription()+" No much Quantity");
            }
        }
        return valid;
    }


    protected boolean isDateWithinThreeDays(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy");
        LocalDateTime inputDate = LocalDateTime.parse(dateString, formatter.withZone(ZoneId.of("Asia/Kolkata")));
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        LocalDateTime threeDaysAgo = currentDate.minus(3, ChronoUnit.DAYS);
        return !inputDate.isBefore(threeDaysAgo);
    }

    private Boolean convertToLocalDateFormat(String dateTimeString){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, dateTimeFormatter);
        LocalDate localDate = localDateTime.toLocalDate();

        Boolean purchaseDateEqualToWeeklyDate = checkWeeklyDate(localDate.format(dateFormatter));
        return purchaseDateEqualToWeeklyDate;
    }
    private boolean checkWeeklyDate(String purchasedate){
        List<LocalDate> dates = new ArrayList<>();
        Boolean vaid = false;
        LocalDate today = LocalDate.now();

        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            dates.add(date);
        }

        L:for (LocalDate date : dates) {
            if(String.valueOf(date).equals(purchasedate)){
                vaid = true;
                break L;
            }else{
                vaid = false;
            }
        }
        return vaid;
    }

    private String convertDateFormat(String inputDateStr) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date inputDate = null;
        try {
            inputDate = inputDateFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputDateFormatObj = new SimpleDateFormat("yyyy-MM-dd");
        return outputDateFormatObj.format(inputDate);
    }
}
