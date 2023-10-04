package com.example.taskmanager.controllers;

import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserRepository userDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        String defaultProfilePic = "/img/user-circle-icon-png-modified.png";


        User proposedUser = new User();
        User userNameInspection = userDao.findByUsername(user.getUsername());
        User userEmailInpsection = userDao.findByEmail(user.getEmail());
        /** Validation for unique fields in database: username & email*/
        if(userNameInspection != null) {
            return "redirect:/sign-up?invalidUsername";
        } else if (userEmailInpsection != null) {
            return "redirect:/sign-up?invalidEmail";
        } else {
            proposedUser.setFirstName(user.getFirstName());
            proposedUser.setLastName(user.getLastName());
            proposedUser.setUsername(user.getUsername());
            proposedUser.setProfilePic(defaultProfilePic);
            proposedUser.setEmail(user.getEmail());
            proposedUser.setPassword(hash);
            userDao.save(proposedUser);
            return "redirect:/login";
        }
    }
}

