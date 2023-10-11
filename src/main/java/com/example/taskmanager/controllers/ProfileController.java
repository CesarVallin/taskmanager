package com.example.taskmanager.controllers;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.CategoryRepository;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

        /** This is utilized for the navbar edit-profile & offcanvas functionality*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = userDao.findById(loggedInUser.getId()).get();
            model.addAttribute("user", currentUser);
        }
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
        /** Initialize list for scheduled / unscheduled tasks */
        List<Task> scheduledTasks = new ArrayList<>();
        List<Task> unscheduledTasks = new ArrayList<>();
        /** Conditional set up LOOP for scheduled / unscheduled tasks lists*/
        for (Task task : tasks) {
            if (task.getDateScheduled().getTime() > 0) { // Filter scheduled tasks based on the default date
                scheduledTasks.add(task);
            } else {
                unscheduledTasks.add(task);
            }
        }

        /** Sort scheduled tasks by dateScheduled */
        scheduledTasks.sort(Comparator.comparing(Task::getDateScheduled));

        // being used in columns (COMPLETE, INCOMPLETE)...
        model.addAttribute("completeTasks", completeTasks);
        model.addAttribute("incompleteTasks", incompleteTasks);
        // (SCHEDULED / UNSCHEDULED)
        model.addAttribute("scheduledTasks", scheduledTasks);
        model.addAttribute("unscheduledTasks", unscheduledTasks);
        // User
        model.addAttribute("user", currentUser);
        // tasks, tasks, not used as of now...
        model.addAttribute("tasks", tasks);

        return "profile/profile-index";
    }

//    @PostMapping("/profile/edit/{id}")
//    public String updateUserInfo(@ModelAttribute User user, @PathVariable long id) {
//
//        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        User editedUser = userDao.findById(id).get();
//
//        /** Validation for unique fields in database: username & email*/
//        if(loggedInUser.getId() == editedUser.getId()) {
//            User userNameInspection = userDao.findByUsername(user.getUsername());
//            User userEmailInpsection = userDao.findByEmail(user.getEmail());
//            if(userNameInspection != null && (userNameInspection.getId() != loggedInUser.getId())) {
//                return "redirect:/profile?invalidUsername";
//            } else if (userEmailInpsection != null && (userEmailInpsection.getId() != loggedInUser.getId())) {
//                return "redirect:/profile?invalidEmail";
//            } else {
//                editedUser.setFirstName(user.getFirstName());
//                editedUser.setLastName(user.getLastName());
//                editedUser.setUsername(user.getUsername());
//                editedUser.setEmail(user.getEmail());
//                userDao.save(editedUser);
//                return "redirect:/login";
//            }
//        }
//        return "redirect:/profile";
//    }

    @PutMapping("/profile/edit/{id}")
    @ResponseBody
    public User userToUpdate(@RequestBody User user, @PathVariable long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User editedUser = userDao.findById(id).get();
        User invalidUser = new User();
        invalidUser.setId(-1);

        if(loggedInUser.getId() == editedUser.getId()) {
            User userNameInspection = userDao.findByUsername(user.getUsername());
            User userEmailInpsection = userDao.findByEmail(user.getEmail());
            if(userNameInspection != null && (userNameInspection.getId() != loggedInUser.getId())) {
//            if(userDao.existsByUsername(user.getUsername())) {
                invalidUser.setUsername("Username is invalid");
                return invalidUser;
//                return "redirect:/profile?invalidUsername";
            } else if (userEmailInpsection != null && (userEmailInpsection.getId() != loggedInUser.getId())) {
//            } else if (userDao.existsByEmail(user.getEmail())) {
                invalidUser.setUsername("Email is invalid");
                return invalidUser;
//                return "redirect:/profile?invalidEmail";
            } else {
                editedUser.setFirstName(user.getFirstName());
                editedUser.setLastName(user.getLastName());
                editedUser.setUsername(user.getUsername());
                editedUser.setEmail(user.getEmail());
                userDao.save(editedUser);
                return editedUser;
//                return "redirect:/login";
            }

        }
        invalidUser.setUsername("You are trying to change a different user, naughty boy");
        return invalidUser;
    }


    @PutMapping("/profile/profile-pic")
    @ResponseBody
    public User insertProfilePic(@RequestBody User user)  {
        User loggedInUser = userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        loggedInUser.setProfilePic(user.getProfilePic());
        userDao.save(loggedInUser);
        return loggedInUser;
    }

    @PutMapping("/profile/default-pic")
    @ResponseBody
    public void insertDefaultProfilePic(@RequestBody User user) {
        User loggedInUser = userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        loggedInUser.setProfilePic(user.getProfilePic());
        userDao.save(loggedInUser);
    }

}
