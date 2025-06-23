package com.alina.orderapp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Order() {}

    public Order(String description, Double amount,String customer_name,String customer_email,OrderStatus orderStatus) {
        this.description = description;
        this.amount = amount;
        this.customerName=customer_name;
        this.customerEmail=customer_email;
        this.status=orderStatus;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDateTime.now();
    }

    public void setId(UUID id){this.id=id;}
    public void setDescription(String description){
        this.description=description;
    }
    public void setAmount(Double amount){
        this.amount=amount;
    }
    public void setCustomerName(String customerName){this.customerName=customerName;}
    public void setCustomerEmail(String customerEmail){this.customerEmail=customerEmail;}
    public void setStatus(OrderStatus status){this.status=status;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}

    public UUID getId(){
        return id;
    }
    public String getDescription(){
        return description;
    }
    public Double getAmount(){
        return amount;
    }
    public LocalDateTime getCreatedAt(){return createdAt;}
    public String getCustomerName(){return customerName;}
    public String getCustomerEmail(){return customerEmail;}
    public OrderStatus getStatus(){return status;}

}
