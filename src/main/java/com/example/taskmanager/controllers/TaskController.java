package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Category;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.CategoryRepository;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String createATaskPost(@ModelAttribute Task task) {

        /** User*/
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findById(loggedInUser.getId()).get();

        /** Date - get current date for task created */
        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());


        /** Category - to set a category id for the task */
        Long categoryId = task.getCategory().getId();
        Category selectedCategory = categoryDao.findById(categoryId).get();

        task.setUser(currentUser);
        task.setDateCreated(currentDate);
        task.setCategory(selectedCategory);
        taskDao.save(task);
        return "redirect:/profile";
    }

    @PostMapping("/task/delete/{id}")
    public String deleteIndividualTask(@ModelAttribute Task task, @PathVariable long id) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(taskDao.existsById(id)) {
            Task taskToDelete = taskDao.findById(id).get();
            if (taskToDelete.getUser().getId() == loggedInUser.getId()) {
                taskDao.delete(taskToDelete);
                return "redirect:/profile";
            }
        }
        return "redirect:/profile";
    }

    @GetMapping("/task/edit/{id}")
    public String editIndividualTask(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (taskDao.existsById(id)) {
            Task taskToEdit = taskDao.findById(id).get();
            if(taskToEdit.getUser().getId() == loggedInUser.getId()) {
                List<Category> categories = categoryDao.findAll();
                model.addAttribute("categories", categories);
                model.addAttribute("task", taskToEdit);

                return "task/task-edit";
            }
        }
        return "redirect:/profile";
    }

    @PostMapping("/task/edit/{id}")
    public String updateEditedTask(@ModelAttribute Task task, @PathVariable long id){
        /** User*/
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findById(loggedInUser.getId()).get();

        /** Category - to set a category id for the task */
        Long categoryId = task.getCategory().getId();
        Category selectedCategory = categoryDao.findById(categoryId).get();

        Task taskToUpdate = taskDao.findById(id).get();

        // NEED TO ACTUALLY SAVE IT IN FULL including complete/incomplete
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setDateScheduled(task.getDateScheduled());
        taskToUpdate.setCategory(selectedCategory);
        taskToUpdate.setUser(currentUser);
        taskDao.save(taskToUpdate);
        return "redirect:/profile";
    }
}
