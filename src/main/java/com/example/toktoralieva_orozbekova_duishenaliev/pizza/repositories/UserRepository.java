package com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String fullName);
    List<User> findAll();
}
