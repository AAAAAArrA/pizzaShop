package com.example.toktoralieva_orozbekova_duishenaliev.pizza.controller;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.AddressDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.UserDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.DeliveryAddress;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CustomUserService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.OrderService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.SmmpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final CustomUserService customUserService;

    private final OrderService orderService;
    private final SmmpService smmpService;

    public UserController(CustomUserService customUserService, OrderService orderService, SmmpService smmpService) {
        this.customUserService = customUserService;
        this.orderService = orderService;
        this.smmpService = smmpService;

    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("address", new AddressDTO());

        return "userForm";
    }

    @PostMapping("/new")
    public String saveUser(UserDTO userDTO, AddressDTO addressDTO, Model model) {
        if (customUserService.save(userDTO)) {
            smmpService.doAction("openAcc", userDTO.getLogin(), BigDecimal.valueOf(-1));
            smmpService.doAction("openAcc", userDTO.getLogin(), BigDecimal.valueOf(-1));

            User user = customUserService.findUser(userDTO.getLogin());
            DeliveryAddress address = customUserService.saveAddress(addressDTO);
            user.getDeliveryAddresses().add(address);
            user.setDeliveryAddress(address);

            customUserService.save(user);

            return "redirect:/";
        } else {
            model.addAttribute("user", userDTO);
            model.addAttribute("address", addressDTO);
            return "userForm";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        customUserService.deleteUser(id);
        return "redirect:/users";

    }

    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You are not authorize");
        }
        User user = customUserService.findUser(principal.getName());

        UserDTO userDTO = UserDTO.builder()
                .login(user.getLogin())
                .role(user.getRole().toString())
                .addresses(user.getDeliveryAddresses().stream()
                        .map(address -> new AddressDTO(address.getId(), address.getStreet(), address.getHousenNumber(), address.getTown(), address.getPostalCode()))
                        .collect(Collectors.toSet()))
                .build();


        model.addAttribute("user", userDTO);
        model.addAttribute("orders", orderService.getOrderByUser(principal.getName()));
        model.addAttribute("balance", smmpService.doAction("balance", userDTO.getLogin(), BigDecimal.valueOf(-1)).getDescription());


        System.out.println(userDTO.getAddresses().size());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateUserProfile(UserDTO dto, Model model, Principal principal) {
        System.out.println(dto);
        if (principal == null) {
            throw new RuntimeException("You are not authorize");
        }
        if (dto.getPassword() != null
                && !dto.getPassword().isEmpty()
                && !Objects.equals(dto.getPassword(), dto.getPasswordMatching())) {
            model.addAttribute("user", dto);
            return "profile";
        }
        customUserService.updateProfile(dto);
        return "redirect:/users/profile";


    }

    @GetMapping("/new-address")
    public String newAddress(Model model) {
        model.addAttribute("address", new AddressDTO());
        return "addressForm";
    }

    @PostMapping("/new-address")
    public String saveAddress(AddressDTO addressDTO, Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You are not authorize");
        }
        User user = customUserService.findUser(principal.getName());
        DeliveryAddress address = customUserService.saveAddress(addressDTO);
        user.getDeliveryAddresses().add(address);
        user.setDeliveryAddress(address);

        customUserService.save(user);

        return "redirect:/users/profile";
    }
}
