package com.example.toktoralieva_orozbekova_duishenaliev.pizza;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.CartDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PizzaDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Cart;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.CartDetails;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Pizza;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.enums.Size;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.CartDetailsRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.CartRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.PizzaRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CustomUserService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.OrderService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartDetailsRepository cartDetailsRepository;

    @Mock
    private CustomUserService customUserService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void testCreateBucket() {
        User user = new User();
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setId(1L);
        Pizza pizza = new Pizza();
        when(pizzaRepository.getById(pizzaDTO.getId())).thenReturn(pizza);
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart result = cartService.createBucket(user, pizzaDTO);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertFalse(result.getCartDetails().isEmpty());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    public void testAddProductToCart_ExistingProduct() {
        Cart cart = new Cart();
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setId(1L);
        pizzaDTO.setSize(Size.SMALL);
        pizzaDTO.setAmount(2);
        CartDetails existingDetail = new CartDetails();
        existingDetail.setAmount(1);
        List<CartDetails> existingCartDetails = Collections.singletonList(existingDetail);
        when(cartDetailsRepository.findByPizzaIdAndCartAndSize(pizzaDTO.getId(), cart, pizzaDTO.getSize()))
                .thenReturn(existingCartDetails);

        cartService.addProductToCart(cart, pizzaDTO);

        assertEquals(3, existingDetail.getAmount());
        verify(cartDetailsRepository).save(existingDetail);
    }

    @Test
    public void testAddProductToCart_NewProduct() {
        // Setup
        Cart cart = new Cart();
        cart.setCartDetails(new ArrayList<>());
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setId(1L);
        pizzaDTO.setSize(Size.MEDIUM);
        pizzaDTO.setAmount(2);
        Pizza pizza = new Pizza();
        pizza.setPriceMedium(100);
        when(pizzaRepository.getById(pizzaDTO.getId())).thenReturn(pizza);
        when(cartDetailsRepository.findByPizzaIdAndCartAndSize(pizzaDTO.getId(), cart, pizzaDTO.getSize()))
                .thenReturn(Collections.emptyList());

        // Act
        cartService.addProductToCart(cart, pizzaDTO);

        // Assert
        assertFalse(cart.getCartDetails().isEmpty());
        CartDetails addedDetail = cart.getCartDetails().get(0);
        assertEquals(pizzaDTO.getAmount(), addedDetail.getAmount());
        assertEquals(pizzaDTO.getSize(), addedDetail.getSize());
        assertEquals(pizza.getPriceMedium(), addedDetail.getPrice());
        verify(pizzaRepository).getById(pizzaDTO.getId());
        verify(cartDetailsRepository, times(2)).save(any(CartDetails.class)); // Expecting two saves now
        verify(cartRepository).save(cart);
    }

    @Test
    public void testGetBucketByUser() {
        String userName = "User1";
        User user = new User();
        Cart cart = new Cart();
        cart.setCartDetails(new ArrayList<>());
        user.setCart(cart);
        when(customUserService.findUserByFullName(userName)).thenReturn(user);

        CartDTO result = cartService.getBucketByUser(userName);

        assertNotNull(result);
        verify(customUserService).findUserByFullName(userName);
    }


    @Test
    public void testDeletePizzaFromCart() {
        String userName = "User1";
        User user = new User();
        Cart cart = new Cart();

        CartDetails cartDetails = new CartDetails();
        cartDetails.setId(1L);
        List<CartDetails> cartDetailsList = new ArrayList<>();
        cartDetailsList.add(cartDetails);
        cart.setCartDetails(cartDetailsList);

        user.setCart(cart);
        when(customUserService.findUserByFullName(userName)).thenReturn(user);

        cartService.deletePizzaFromCart(1L, userName);

        verify(cartRepository).save(cart);
    }



}

