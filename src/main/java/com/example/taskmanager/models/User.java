package com.example.taskmanager.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String profilePic;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private List<Task> tasks;

    /** For authentication */
    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        firstName = copy.firstName;
        lastName = copy.lastName;
        username = copy.username;
        profilePic = copy.profilePic;
        email = copy.email;
        password = copy.password;
        tasks = copy.tasks;
    }

    public User() {

    }

    public User(String firstName, String lastName, String username, String profilePic, String email, String password, List<Task> tasks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.profilePic = profilePic;
        this.email = email;
        this.password = password;
        this.tasks = tasks;
    }
}
