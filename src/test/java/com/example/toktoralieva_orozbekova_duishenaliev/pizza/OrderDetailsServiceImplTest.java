package com.example.toktoralieva_orozbekova_duishenaliev.pizza;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.OrderDetails;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.OrderDetailsRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation.OrderDetailsServiceImpl;
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
public class OrderDetailsServiceImplTest {

    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderDetailsServiceImpl orderDetailsService;

    @Test
    public void testGetDetails() {
        List<OrderDetails> orderDetailsList = Arrays.asList(new OrderDetails(), new OrderDetails());
        Mockito.when(orderDetailsRepository.findAll()).thenReturn(orderDetailsList);

        List<OrderDetails> result = orderDetailsService.getDetails();

        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(orderDetailsRepository).findAll();
    }


}

