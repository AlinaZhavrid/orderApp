package com.alina.orderapp.service;

import com.alina.orderapp.model.Order;
import com.alina.orderapp.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @Transactional
    public Order createOrder(Order order){
        validateOrder(order);
        return orderRepository.save(order);
    }

    public Order getOrderById(UUID id){
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return orderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Order not found with id "+id));
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Page<Order> getAllOrders(int page, int size){
        if (page<0) throw new IllegalArgumentException("Page must be>=0");
        if (size<=0) throw new IllegalArgumentException("Size must be>0");
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return orderRepository.findAll(pageable);
    }

    @Transactional
    public Order updateOrder(UUID id, Order orderDetails){
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        Order updatedOrder=getOrderById(id);

        updatedOrder.setDescription(orderDetails.getDescription());
        updatedOrder.setAmount(orderDetails.getAmount());
        return orderRepository.save(updatedOrder);
    }


    @Transactional
    public void deleteOrder(UUID id){
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        Order order=getOrderById(id);
        orderRepository.delete(order);
    }

    private void validateOrder(Order order){
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (order.getDescription() == null || order.getDescription().isBlank()) {
            throw new IllegalArgumentException("Order description cannot be empty");
        }

        if (order.getAmount() == null || order.getAmount() <= 0) {
            throw new IllegalArgumentException("Order amount must be positive");
        }
    }
}
