package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.UserDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    boolean save(UserDTO userDTO);
    void save(User user);
    List<User> allUsers();
    void delete(Long id);
    User findUser(String userName);
    void updateProfile(UserDTO dto);

}
