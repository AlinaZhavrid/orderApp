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
    private LocalDateTime createdAt;

    public Order() {
    }

    public Order(String description, Double amount) {
        this.description = description;
        this.amount = amount;
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

}
