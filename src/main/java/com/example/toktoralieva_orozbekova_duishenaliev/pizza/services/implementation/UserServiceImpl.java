package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.AddressDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.UserDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.enums.Role;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.DeliveryAddress;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.UserRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean save(UserDTO userDTO) {
        try {
            User user = User.builder()
                    .login(userDTO.getLogin())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    // Assuming you have a method to convert DTO address to Entity address
                    .deliveryAddresses(convertAddressDtoToEntity(userDTO.getAddresses()))
                    .role(Role.CLIENT) // Set the role, ensure this aligns with your logic
                    .build();
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Set<DeliveryAddress> convertAddressDtoToEntity(Set<AddressDTO> addressDTOs) {
        if (addressDTOs == null || addressDTOs.isEmpty()) {
            return Collections.emptySet();
        }

        return addressDTOs.stream()
                .map(dto -> new DeliveryAddress(
                        dto.getStreet(),
                        dto.getHouseNumber(),
                        dto.getTown(),
                        dto.getPostalCode())
                )
                .collect(Collectors.toSet());
    }


    @Override
    @Transactional
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUser(String login) {

        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional
    public void updateProfile(UserDTO dto) {
        User savedUser = userRepository.findByLogin(dto.getLogin());
        if (savedUser == null) {
            throw new RuntimeException("User not found " + dto.getLogin());
        }
        boolean isChanged = false;

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }

        if (!Objects.equals(dto.getLogin(), savedUser.getLogin())) {
            savedUser.setLogin(dto.getLogin());
            isChanged = true;
        }

        if (isChanged) {
            userRepository.save(savedUser);
        }
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


}
