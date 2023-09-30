package com.example.taskmanager.controllers;

import com.example.taskmanager.repositories.CategoryRepository;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private UserRepository userDao;
    private TaskRepository taskDao;
    private CategoryRepository categoryDao;

    public ProfileController(UserRepository userDao, TaskRepository taskDao, CategoryRepository categoryDao) {
        this.userDao = userDao;
        this.taskDao = taskDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping("/profile")
    public String profileIndexPage() {
        return "profile/index";
    }
}
