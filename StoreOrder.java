package com.ecommerce.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="store_orders")
public class StoreOrder {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String items;
    private double total;
    private String status;
    private LocalDateTime createdAt;

    public StoreOrder() {}
    public StoreOrder(String customerName, String items, double total) {
        this.customerName=customerName; this.items=items; this.total=total;
        this.status="PLACED"; this.createdAt=LocalDateTime.now();
    }
    public Long getId(){return id;}
    public String getCustomerName(){return customerName;}
    public String getItems(){return items;}
    public double getTotal(){return total;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
