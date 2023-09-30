package com.example.taskmanager.repositories;

import com.example.taskmanager.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Long> {
}
