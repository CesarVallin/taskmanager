package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Category;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.CategoryRepository;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.List;
import java.sql.Date;

@Controller
public class TaskController {

    private UserRepository userDao;
    private TaskRepository taskDao;
    private CategoryRepository categoryDao;

    public TaskController(UserRepository userDao, TaskRepository taskDao, CategoryRepository categoryDao) {
        this.userDao = userDao;
        this.taskDao = taskDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping("/task/create")
    public String createATaskView(Model model) {
        List<Category> categories = categoryDao.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("task", new Task());
        return "task/task-create";
    }

    @PostMapping("/task/create")
    public String createATaskPost(@ModelAttribute Task task, @RequestParam(name = "dateScheduled") Date dateScheduled) {
        /** User*/
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUserId = userDao.findById(loggedInUser.getId()).get();

        /** Date - get current date for task created */
        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());

        /** Does not work :*******************************/
        String dateInput = dateScheduled.toString();
        System.out.println(dateInput);
        if(dateInput.isEmpty()) {
            task.setDateScheduled(null);
        }

        /** Category - to set a category id for the task */
        Long categoryId = task.getCategory().getId();
        Category selectedCategory = categoryDao.findById(categoryId).get();

        task.setUser(currentUserId);
        task.setDateCreated(currentDate);
        task.setCategory(selectedCategory);
        taskDao.save(task);
        return "redirect:/profile";
    }
}
