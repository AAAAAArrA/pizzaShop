package com.example.toktoralieva_orozbekova_duishenaliev.pizza.controller;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService; // Сервис для работы с пользователями

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.allUsers(); // Получение списка всех пользователей
        model.addAttribute("users", users);
        return "admin_users"; // Thymeleaf шаблон для отображения пользователей
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id); // Удаление пользователя по ID
        return "redirect:/admin/users";
    }
}

