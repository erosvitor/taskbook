package com.ctseducare.taskbook.unittest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ctseducare.taskbook.exception.ResourceNotFoundException;
import com.ctseducare.taskbook.model.Task;
import com.ctseducare.taskbook.repository.TaskRepository;
import com.ctseducare.taskbook.service.TaskService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
	
	@Mock
	TaskRepository repository;
	
	@InjectMocks
	TaskService service;

	@Test
	void testCreate() {
		Task taskToCreate = new Task();
		taskToCreate.setDescription("Tarefa 1");
		taskToCreate.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskToCreate.setFinalDate(LocalDateTime.of(2023, 9, 25, 18, 0, 0));

		Task taskCreated = new Task();
		taskCreated.setId(1);
		taskCreated.setDescription("Tarefa 1");
		taskCreated.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskCreated.setFinalDate(LocalDateTime.of(2023, 9, 25, 18, 0, 0));
		
		when(repository.save(taskToCreate)).thenReturn(taskCreated);
		Task result = service.create(taskToCreate);
		
		assertNotNull(result);
		assertEquals(result.getId(), taskCreated.getId());
	}

	@Test
	void testReadAll() {
		Task task = new Task();
		task.setId(1);
		task.setDescription("Tarefa 1");
		task.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		task.setFinalDate(LocalDateTime.of(2023, 9, 25, 18, 0, 0));
		
		List<Task> tasks = new ArrayList<>();
		tasks.add(task);
		
		when(repository.findAll()).thenReturn(tasks);
		List<Task> result = service.readAll();
		
		assertNotNull(result);
		assertEquals(result.size(), tasks.size());
	}
	
	@Test
	void testUpdateSuccess() {
		Task taskToUpdate = new Task();
		taskToUpdate.setId(1);
		taskToUpdate.setDescription("Tarefa 1");
		taskToUpdate.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskToUpdate.setFinalDate(LocalDateTime.of(2023, 9, 28, 18, 0, 0));

		Task taskFounded = new Task();
		taskFounded.setId(1);
		taskFounded.setDescription("Tarefa 1");
		taskFounded.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskFounded.setFinalDate(LocalDateTime.of(2023, 9, 28, 18, 0, 0));

		Task taskUpdated = new Task();
		taskUpdated.setId(1);
		taskUpdated.setDescription("Tarefa 1");
		taskUpdated.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskUpdated.setFinalDate(LocalDateTime.of(2023, 9, 28, 18, 0, 0));

		when(repository.findById(taskToUpdate.getId())).thenReturn(Optional.of(taskFounded));
		when(repository.save(taskToUpdate)).thenReturn(taskUpdated);
		Task result = service.update(taskToUpdate);

		assertNotNull(result);
		assertEquals(result.getId(), taskUpdated.getId());
	}

	@Test
	void testUpdateTaskDoesNotExist() {
		Task taskToUpdate = new Task();
		taskToUpdate.setId(2);
		taskToUpdate.setDescription("Tarefa não exite");
		taskToUpdate.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskToUpdate.setFinalDate(LocalDateTime.of(2023, 9, 28, 18, 0, 0));
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.update(taskToUpdate);
		});
		
		assertNotNull(exception);
		assertEquals("Task does not exist!", exception.getMessage());
	}
	
	@Test
	void testDeleteSuccess() {
		Integer taskId = 1;
		
		Task taskFounded = new Task();
		taskFounded.setId(1);
		taskFounded.setDescription("Tarefa 1");
		taskFounded.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskFounded.setFinalDate(LocalDateTime.of(2023, 9, 28, 18, 0, 0));

		when(repository.findById(taskId)).thenReturn(Optional.of(taskFounded));
		Task result = service.delete(1);

		assertNotNull(result);
		assertEquals(result.getId(), taskFounded.getId());
	}

	@Test
	void testDeleteTaskDoesNotExist() {
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(2);
		});

		assertNotNull(exception);
		assertEquals("Task does not exist!", exception.getMessage());
	}

}
