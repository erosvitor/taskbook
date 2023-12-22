package com.ctseducare.taskbook.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctseducare.taskbook.exception.ResourceNotFoundException;
import com.ctseducare.taskbook.model.Task;
import com.ctseducare.taskbook.repository.TaskRepository;

@Service
public class TaskService {
	
	Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    TaskRepository repository;
    
    public Task create(Task task) {
        return repository.save(task);
    }
    
    public List<Task> readAll() {
        return repository.findAll();
    }
    
    public Task update(Task task) {
    	Optional<Task> taskInRepository = repository.findById(task.getId());
    	if (!taskInRepository.isPresent()) {
    		logger.error("Task does not exist!");
    		throw new ResourceNotFoundException("Task does not exist!");
    	}
        return repository.save(task);
    }
    
    public Task delete(Integer id) {
    	Optional<Task> taskInRepository = repository.findById(id);
    	if (!taskInRepository.isPresent()) {
    		logger.error("Task does not exist!");
    		throw new ResourceNotFoundException("Task does not exist!");
    	}
    	repository.deleteById(id);
    	return taskInRepository.get();
    }
    
}