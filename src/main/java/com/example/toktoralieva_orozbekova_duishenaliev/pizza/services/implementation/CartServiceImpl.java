package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.CartDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.CartDetailsDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PayActionResponseDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PizzaDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.*;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.enums.Size;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.CartDetailsRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.CartRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.PizzaRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CartService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CustomUserService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.OrderService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Order;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.SmmpService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private final PizzaRepository pizzaRepository;
    private final CartRepository cartRepository;

    private final CartDetailsRepository cartDetailsRepository;

    private final CustomUserService customUserService;
    private final OrderService orderService;
    private final SmmpService smmpService;

    public CartServiceImpl(PizzaRepository pizzaRepository, CartRepository cartRepository, CartDetailsRepository cartDetailsRepository, CustomUserService customUserService, OrderService orderService, SmmpService smmpService) {
        this.pizzaRepository = pizzaRepository;
        this.cartRepository = cartRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.customUserService = customUserService;
        this.orderService = orderService;
        this.smmpService = smmpService;
    }

    @Override
    public Cart createBucket(User user, PizzaDTO pizzaDTO) {
        Cart cart = new Cart();
        cart.setUser(user);
//        List<Pizza> pizzaList = getCollectRefPizzasById(productIds);
        List<CartDetails> list = new ArrayList<>();
        CartDetails cartDetails = new CartDetails();
        cartDetails.setCart(cart);
        cartDetails.setPizza(pizzaRepository.getById(pizzaDTO.getId()));
        cartDetails.setSize(pizzaDTO.getSize());
        cartDetails.setAmount(pizzaDTO.getAmount());
        cartDetails.setPrice(pizzaDTO.getPrice());
        list.add(cartDetails);
        cart.setCartDetails(list);
        return cartRepository.save(cart);
    }

//    @Override
//    public List<Pizza> getCollectRefPizzasById(List<Long> productIds) {
//        return productIds.stream()
//                //getOne вытаскивает ссылку на объект, а findById - сам объект
//                .map(pizzaRepository::getOne)
//                .collect(Collectors.toList());
//    }



    @Override
    @Transactional
    public void addProductToCart(Cart cart, PizzaDTO pizzaDTO) {

        if (!cartDetailsRepository.findByPizzaIdAndCartAndSize(pizzaDTO.getId(), cart, pizzaDTO.getSize()).isEmpty()){
            CartDetails cartDetails = cartDetailsRepository.findByPizzaIdAndCartAndSize(
                    pizzaDTO.getId(), cart, pizzaDTO.getSize()).get(0);

            cartDetails.setAmount(cartDetails.getAmount() + pizzaDTO.getAmount());
            cartDetailsRepository.save(cartDetails);
            return;
        }

        CartDetails cartDetails = new CartDetails();

        Pizza pizza = pizzaRepository.getById(pizzaDTO.getId());

        cartDetails.setPizza(pizza);
        cartDetails.setSize(pizzaDTO.getSize());
        cartDetails.setAmount(pizzaDTO.getAmount());

        if(pizzaDTO.getSize() == Size.SMALL){
            cartDetails.setPrice(pizza.getPriceSmall());
        }else if(pizzaDTO.getSize() == Size.MEDIUM){
            cartDetails.setPrice(pizza.getPriceMedium());
        }else if(pizzaDTO.getSize() == Size.LARGE){
            cartDetails.setPrice(pizza.getPriceLarge());
        }

        cartDetailsRepository.save(cartDetails);

        cartDetails.setCart(cart);
        cart.getCartDetails().add(cartDetails);

        cartDetailsRepository.save(cartDetails);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getBucketByUser(String name) {
        User user = customUserService.findUserByFullName(name);
        if(user == null || user.getCart() == null){
            return new CartDTO();
        }
        CartDTO cartDTO = new CartDTO();

        cartDTO.setCartDetails(
                getCartDetailsDTOByCart(user.getCart())
        );
        cartDTO.aggregate();

        return cartDTO;
    }

    @Override
    @Transactional
    public void deletePizzaFromCart(Long id,String name){
//        User user = customUserService.findUserByFullName(name);
//        if(user == null || user.getCart() == null){
//            return new CartDTO();
//        }
//        CartDTO cartDTO = new CartDTO();
//        List<CartDetails> pizzas = user.getCart().getCartDetails();
//        for(CartDetails cartDetails : pizzas){
//            if(cartDetails.getId() == id){
//                pizzas.remove(cartDetails);
//            }
//        }
//        user.getCart().getCartDetails().clear();
//        user.getCart().setCartDetails(pizzas);
//
//        Map<Long, CartDetailsDTO> mapByPizzaId = new HashMap<>();
//        List<CartDetails> p = user.getCart().getCartDetails();
//        for(CartDetails pizza : p){
//            CartDetailsDTO details = mapByPizzaId.get(pizza.getId());
//            if(details == null){
//                mapByPizzaId.put(pizza.getId(), new CartDetailsDTO(pizza));
//            }else{
//                details.setAmount(details.getAmount());
//                details.setSum(details.getSum() + Double.parseDouble(String.valueOf(pizza.getPrice())));
//            }
//        }
//
//        cartDTO.setCartDetails(new ArrayList<>(mapByPizzaId.values()));
//        cartDTO.aggregate();
//        return cartDTO;

        User user = customUserService.findUserByFullName(name);
        Cart cart = user.getCart();

        if(cart == null || cart.getCartDetails().isEmpty()){
            return;
        }

        List<CartDetails> cartDetailsList = cart.getCartDetails();
        for (CartDetails cartDetails: cartDetailsList){
            if (cartDetails.getId() == id){
                cartDetailsList.remove(cartDetails);
                cartDetails.setCart(null);
                break;
            }
        }

        cart.setCartDetails(cartDetailsList);
        cartRepository.save(cart);

    }

    public List<CartDetailsDTO> getCartDetailsDTOByCart(Cart cart){

        List<CartDetails> pizzas = cart.getCartDetails();
        List<CartDetailsDTO> cartDetailsDTOS = new ArrayList<>();
        for(CartDetails cartDetails : pizzas){

            CartDetailsDTO details = new CartDetailsDTO();
            details.setAmount(cartDetails.getAmount());
            details.setSum(cartDetails.getPrice() * cartDetails.getAmount());
            details.setTitle(cartDetails.getPizza().getName() + " (" + cartDetails.getSize() + ")");
            details.setPrice(cartDetails.getPrice());
            details.setPizzaId(cartDetails.getId());

            cartDetailsDTOS.add(details);
        }

        return cartDetailsDTOS;
    }

    @Override
    @Transactional
    public void commitBucketToOrder(String name) {
        User user = customUserService.findUserByFullName(name);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Cart cart = user.getCart();
        if(cart == null || cart.getCartDetails().isEmpty()){
            return;
        }

        Order order = new Order();
        order.setUser(user);

        List<OrderDetails> orderList = new ArrayList<>();
        List<CartDetails> cartDetailsList = cart.getCartDetails();
        double total = 0;

        for (CartDetails cartDetails: cartDetailsList){
            OrderDetails orderDetails = new OrderDetails();

            orderDetails.setOrder(order);
            orderDetails.setAmount(cartDetails.getAmount());
            orderDetails.setPizza(cartDetails.getPizza());
            orderDetails.setSize(cartDetails.getSize());
            orderDetails.setPrice(cartDetails.getPrice());

            total += cartDetails.getPrice() * cartDetails.getAmount();
            cartDetails.setCart(null);
            orderList.add(orderDetails);
        }

        order.setDetails(orderList);
        order.setSumm(total);

        orderService.save(order);
        cartDetailsList.clear();
        cartRepository.save(cart);
        cartDetailsRepository.saveAll(cartDetailsList);

        this.smmpService.doAction("transfer", user.getLogin(), BigDecimal.valueOf(total));
    }
}
