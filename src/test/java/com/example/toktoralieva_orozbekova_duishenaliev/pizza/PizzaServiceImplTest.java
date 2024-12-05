package com.example.toktoralieva_orozbekova_duishenaliev.pizza;



import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PizzaDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Cart;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Pizza;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.CartRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.PizzaRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CartService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CustomUserService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation.PizzaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;



import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceImplTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private CustomUserService customUserService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private PizzaServiceImpl pizzaService;

    @Test
    public void testSavePizzaToDB() throws IOException {
        Pizza pizza = new Pizza();
        MultipartFile image = Mockito.mock(MultipartFile.class);
        Mockito.when(image.getBytes()).thenReturn(new byte[] {});
        Mockito.when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizza);

        Pizza result = pizzaService.savePizzaToDB(pizza, image);

        assertNotNull(result);
        assertEquals(1, result.getEnabled());
        Mockito.verify(pizzaRepository).save(any(Pizza.class));
    }

    @Test
    public void testDeletePizza() {
        Pizza pizza = new Pizza();
        pizza.setEnabled(1);
        Long pizzaId = 1L;
        Mockito.when(pizzaRepository.getById(pizzaId)).thenReturn(pizza);

        pizzaService.deletePizza(pizzaId);

        assertEquals(0, pizza.getEnabled());
        Mockito.verify(pizzaRepository).save(pizza);
    }

    @Test
    public void testGetPizzaById() {
        Long pizzaId = 1L;
        Pizza pizza = new Pizza();
        Mockito.when(pizzaRepository.getById(pizzaId)).thenReturn(pizza);

        Pizza result = pizzaService.getPizzaById(pizzaId);

        assertNotNull(result);
        Mockito.verify(pizzaRepository).getById(pizzaId);
    }

    @Test
    public void testGetListOfAllPizza() {
        List<Pizza> pizzaList = Arrays.asList(new Pizza(), new Pizza());
        Mockito.when(pizzaRepository.findAll()).thenReturn(pizzaList);

        List<Pizza> result = pizzaService.getListOfAllPizza();

        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(pizzaRepository).findAll();
    }

    @Test
    public void testAddToUserCart() {
        String userName = "User1";
        User user = new User();
        PizzaDTO pizzaDTO = new PizzaDTO();

        Mockito.when(customUserService.findUserByFullName(userName)).thenReturn(user);
        Mockito.doNothing().when(cartService).addProductToCart(any(Cart.class), any(PizzaDTO.class));

        pizzaService.addToUserCart(pizzaDTO, userName);

        Mockito.verify(customUserService).findUserByFullName(userName);
        Mockito.verify(cartService).addProductToCart(any(Cart.class), any(PizzaDTO.class));
    }




}