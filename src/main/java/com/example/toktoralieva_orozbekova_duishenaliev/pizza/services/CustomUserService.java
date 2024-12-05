package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.AddressDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.UserDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.DeliveryAddress;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CustomUserService {
    User findUser(Long id);
    void save(User user);

    User findUserByFullName(String name);

    public List<User> getAllUsers();

    boolean save(UserDTO userDTO);

    void deleteUser(Long id);
    public User findUser(String userName);

    public void updateProfile(UserDTO dto);

    public boolean saveAdmin(UserDTO userDTO);

    public DeliveryAddress saveAddress(AddressDTO address);

}
