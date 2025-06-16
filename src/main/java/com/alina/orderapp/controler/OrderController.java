package com.alina.orderapp.controler;

import com.alina.orderapp.model.Order;
import com.alina.orderapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order createdOrder=orderService.createOrder(order);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(createdOrder.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id){
        Order order=orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAllOrdersPageble(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<Order>orders=orderService.getAllOrders(page,size);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable UUID id,
                                             @RequestBody Order orderDetails){
        Order updatedOrder=orderService.updateOrder(id,orderDetails);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
