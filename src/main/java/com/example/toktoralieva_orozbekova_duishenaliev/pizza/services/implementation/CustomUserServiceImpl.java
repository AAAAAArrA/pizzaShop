package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.AddressDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.UserDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.enums.Role;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.DeliveryAddress;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.AddressRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.UserRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CustomUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class CustomUserServiceImpl implements CustomUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AddressRepository addressRepository;

    public CustomUserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
    }

    @Override
    public User findUser(Long id) {
        return null;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public User findUserByFullName(String name) {
        return userRepository.findByLogin(name);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public boolean save(UserDTO userDTO) {
        if(!Objects.equals(userDTO.getPassword(), userDTO.getPassword())){
            throw new RuntimeException("Password is not equals");
        }User user = User.builder()
                .login(userDTO.getLogin())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.CLIENT)
                .deliveryAddresses(new HashSet<>())
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean saveAdmin(UserDTO userDTO) {
        if(!Objects.equals(userDTO.getPassword(), userDTO.getPasswordMatching())){
            throw new RuntimeException("Password is not equals");
        }
        User user = User.builder()
                .login(userDTO.getLogin())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public DeliveryAddress saveAddress(AddressDTO addressDTO) {

        DeliveryAddress address = new DeliveryAddress();
        address.setStreet(addressDTO.getStreet());
        address.setHousenNumber(addressDTO.getHouseNumber());
        address.setTown(addressDTO.getTown());
        address.setPostalCode(addressDTO.getPostalCode());

        addressRepository.save(address);

        return address;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUser(String userName) {
        return userRepository.findByLogin(userName);
    }

    @Override
    @Transactional
    public void updateProfile(UserDTO dto) {
        User savedUser = userRepository.findByLogin(dto.getLogin());
        if(savedUser == null){
            throw new RuntimeException("User not found " + dto.getLogin());
        }
        boolean isChanged = false;

        if(dto.getPassword() != null && !dto.getPassword().isEmpty()){
            savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }
        if(isChanged){
            userRepository.save(savedUser);
        }

    }
}
