package com.example.toktoralieva_orozbekova_duishenaliev.pizza;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Order;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.OrderRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CustomUserService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomUserService customUserService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        orderService.save(order);
        Mockito.verify(orderRepository).save(order);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(orderRepository).findAll();
    }

    @Test
    public void testGetOrderByUser() {
        String userName = "User1";
        User user = new User();
        user.setId(1L);
        List<Order> orders = Arrays.asList(new Order(), new Order());

        Mockito.when(customUserService.findUserByFullName(userName)).thenReturn(user);
        Mockito.when(orderRepository.findAllByUserId(user.getId())).thenReturn(orders);

        List<Order> result = orderService.getOrderByUser(userName);

        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(orderRepository).findAllByUserId(user.getId());
    }



}
