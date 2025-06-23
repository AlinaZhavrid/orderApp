package com.alina.orderapp;

import com.alina.orderapp.model.Order;
import com.alina.orderapp.model.OrderStatus;
import com.alina.orderapp.repository.OrderRepository;
import com.alina.orderapp.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Подключаем Mockito
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository; // Мок репозитория

    @InjectMocks
    private OrderService orderService; // Тестируемый сервис

    @Test
    void createOrder_ShouldReturnSavedOrder() {
        Order testOrder = new Order("Test order", 100.0, "Customer", "test@test.com", OrderStatus.NEW);
        Order savedOrder = new Order("Test order", 100.0, "Customer", "test@test.com", OrderStatus.NEW);
        savedOrder.setId(UUID.randomUUID());

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = orderService.createOrder(testOrder);

        assertNotNull(result.getId());
        assertEquals(savedOrder.getId(),result.getId());
        assertEquals(savedOrder.getDescription(), result.getDescription());
        assertEquals(savedOrder.getCustomerName(),result.getCustomerName());
        assertEquals(savedOrder.getCustomerEmail(),result.getCustomerEmail());
        assertEquals(savedOrder.getAmount(),result.getAmount());
        assertEquals(savedOrder.getCreatedAt(),result.getCreatedAt());

        verify(orderRepository, times(1)).save(testOrder);
    }

    @Test
    void getOrderById_WhenOrderExists_ShouldReturnOrder() {
        UUID testId = UUID.randomUUID();
        Order expectedOrder = new Order("Test", 100.0, "Customer", "test@test.com", OrderStatus.NEW);
        expectedOrder.setId(testId);

        when(orderRepository.findById(testId)).thenReturn(Optional.of(expectedOrder));

        Order result = orderService.getOrderById(testId);

        assertEquals(expectedOrder.getId(), result.getId());
        verify(orderRepository, times(1)).findById(testId);
    }

    @Test
    void getOrderById_WhenOrderNotExists_ShouldThrowException() {
        UUID nonExistentId = UUID.randomUUID();

        when(orderRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            orderService.getOrderById(nonExistentId);
        });
    }


    @Test
    void createOrder_WhenCustomerNameNotExist_SholdThrowExeption(){
        Order illegalOrder=new Order("Test", 100.0, null, "test@test.com", OrderStatus.NEW);

        Exception exception=assertThrows(IllegalArgumentException.class,()->{
            orderService.createOrder(illegalOrder);
        });
        assertEquals("Customer name cannot be empty", exception.getMessage());
        verify(orderRepository,never()).save(any());
    }

    @Test
    void createOrder_WhenCustomerEmailNotExist_SholdThrowExeption(){
        Order illegalOrder=new Order("Test", 100.0, "Customer", null, OrderStatus.NEW);
        Exception exception=assertThrows(IllegalArgumentException.class,()->{
            orderService.createOrder(illegalOrder);
        });
        assertEquals("Customer email cannot be empty", exception.getMessage());
        verify(orderRepository,never()).save(any());
    }

    @Test
    void createOrder_WhenAmountIllegal_ShouldThrowExeption(){
        Order illegalOrder=new Order("Test", -100.0, "Customer", "test@test.com", OrderStatus.NEW);
        assertThrows(IllegalArgumentException.class,()->{
            orderService.createOrder(illegalOrder);
        });
        verify(orderRepository,never()).save(any());
    }
}
