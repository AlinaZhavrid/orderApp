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
    private String customer_name;

    @Column(nullable = false)
    private String customer_email;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Order() {}

    public Order(String description, Double amount,String customer_name,String customer_email,OrderStatus orderStatus) {
        this.description = description;
        this.amount = amount;
        this.customer_name=customer_name;
        this.customer_email=customer_email;
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
    public void setCustomerName(String customer_name){this.customer_name=customer_name;}
    public void setCustomerEmail(String customerEmail){this.customer_email=customer_email;}
    public void setStatus(OrderStatus status){this.status=status;}

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
    public String getCustomerName(){return customer_name;}
    public String getCustomerEmail(){return customer_email;}
    public OrderStatus getStatus(){return status;}

}
