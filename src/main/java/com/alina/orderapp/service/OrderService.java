package com.alina.orderapp.service;

import com.alina.orderapp.model.Order;
import com.alina.orderapp.model.OrderStatus;
import com.alina.orderapp.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.NEW);
        }
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

    @Transactional
    public Order updateOrder(UUID id, Order orderUpdates){
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        Order existingOrder=getOrderById(id);

        if (orderUpdates.getDescription() != null) {
            existingOrder.setDescription(orderUpdates.getDescription());
        }
        if (orderUpdates.getAmount() > 0) {
            existingOrder.setAmount(orderUpdates.getAmount());
        }
        if (orderUpdates.getCustomerName() != null) {
            existingOrder.setCustomerName(orderUpdates.getCustomerName());
        }
        if (orderUpdates.getCustomerEmail() != null) {
            existingOrder.setCustomerEmail(orderUpdates.getCustomerEmail());
        }
        if (orderUpdates.getStatus() != null) {
            existingOrder.setStatus(orderUpdates.getStatus());
        }
        return orderRepository.save(existingOrder);
    }

    @Transactional
    public void deleteOrder(UUID id){
        if(!orderRepository.existsById(id)){
            throw new IllegalArgumentException("Order doesnt exist");
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order updateOrderStatus(UUID id, OrderStatus newStatus) {
        Order order = getOrderById(id);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    private void validateOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (order.getDescription() == null || order.getDescription().isBlank()) {
            throw new IllegalArgumentException("Order description cannot be empty");
        }
        if (order.getAmount() <= 0) {
            throw new IllegalArgumentException("Order amount must be positive");
        }
        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        if (order.getCustomerEmail() == null || order.getCustomerEmail().isBlank()) {
            throw new IllegalArgumentException("Customer email cannot be empty");
        }
        if (order.getStatus() != null && !isValidStatus(order.getStatus())) {
            throw new IllegalArgumentException("Invalid order status");
        }
    }

    private boolean isValidStatus(OrderStatus status) {
        for (OrderStatus validStatus : OrderStatus.values()) {
            if (validStatus.equals(status)) {
                return true;
            }
        }
        return false;
    }
}
