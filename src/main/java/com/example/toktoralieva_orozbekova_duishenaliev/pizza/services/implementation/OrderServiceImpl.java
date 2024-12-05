package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.OrderRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CustomUserService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.OrderService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final CustomUserService customUserService;

    public OrderServiceImpl(OrderRepository orderRepository, CustomUserService customUserService) {
        this.orderRepository = orderRepository;
        this.customUserService = customUserService;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

//    @Override
//    public Page<Order> getPaginatedOrdersBuUser(String name, int pageNo, int pageSize) {
//        User user = customUserService.findUserByFullName(name);
//        List<Order> orderList = orderRepository.findAllByUserId(user.getId());
//        return null;
//    }

    @Override
    public Page<Order> getAllPaginatedOrders(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public List<Order> findFilteredOrders(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrderByUser(String name) {
        User user = customUserService.findUserByFullName(name);
        List<Order> orderList = orderRepository.findAllByUserId(user.getId());
        return orderList;
    }
}
