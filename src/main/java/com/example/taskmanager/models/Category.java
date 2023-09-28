package com.example.taskmanager.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "category")
    private List<Task> tasks;



}
