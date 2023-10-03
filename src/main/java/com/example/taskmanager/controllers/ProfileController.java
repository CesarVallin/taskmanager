package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.CategoryRepository;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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
    public String profileIndexPage(Model model) {
        /** User*/
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findById(loggedInUser.getId()).get();
        /** All tasks*/
        List<Task> tasks = taskDao.findByUserId(currentUser.getId());
        /** Initialize list for complete/ incomplete tasks*/
        List<Task> completeTasks = new ArrayList<>();
        List<Task> incompleteTasks = new ArrayList<>();

        /** Conditional set up LOOP for complete & incomplete task lists*/
        for(Task task: tasks) {
            if(task.isComplete()) {
                completeTasks.add(task);
            } else {
                incompleteTasks.add(task);
            }
        }
        // being used in columns...
        model.addAttribute("completeTasks", completeTasks);
        model.addAttribute("incompleteTasks", incompleteTasks);
        // User
        model.addAttribute("user", loggedInUser);
        // tasks, tasks, not used as of now...
        model.addAttribute("tasks", tasks);

        return "profile/profile-index";
    }
}
