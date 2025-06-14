package com.alina.orderapp.service;

import com.alina.orderapp.model.Order;
import com.alina.orderapp.repository.OrderRepository;
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
        return orderRepository.save(order);
    }

    public Order getOrderById(UUID id){
        //TODO exeptions
        return orderRepository.findById(id).orElseThrow();
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrder(UUID id, Order orderDetails){
        Order updatedOrder=getOrderById(id);

        updatedOrder.setDescription(orderDetails.getDescription());
        updatedOrder.setAmount(orderDetails.getAmount());
        return orderRepository.save(updatedOrder);
    }


    @Transactional
    public void deleteOrder(UUID id){
        Order order=getOrderById(id);
        orderRepository.delete(order);
    }

    private void validateOrder(Order order){
        //TODO exeptions
    }
}
