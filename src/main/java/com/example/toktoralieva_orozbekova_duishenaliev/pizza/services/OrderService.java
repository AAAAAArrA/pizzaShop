package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface OrderService {
    void save(Order order);
//    Page<Order> getPaginatedOrdersBuUser(String name, int pageNo, int pageSize);
    Page<Order> getAllPaginatedOrders(int pageNo, int pageSize);
    public List<Order> findFilteredOrders(LocalDateTime start, LocalDateTime end);

    List<Order> getAllOrders();

    public List<Order> getOrderByUser(String name);

}
