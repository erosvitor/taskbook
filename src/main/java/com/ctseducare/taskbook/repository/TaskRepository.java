package com.ctseducare.taskbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctseducare.taskbook.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}