package com.ctseducare.taskbook.unittest.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ctseducare.taskbook.exception.ResourceNotFoundException;
import com.ctseducare.taskbook.model.Task;
import com.ctseducare.taskbook.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TaskService service;
	
	@Test
	void testStatus() throws Exception {
		// When
		var request = MockMvcRequestBuilders.get("/tasks/status");

		ResultActions response = mockMvc.perform(request);
		
		// Then
		response
		  //.andDo(print())
		  .andExpect(status().is(200));
	}

	@Test
	void testPost() throws Exception {
		Task taskToCreate = new Task();
		taskToCreate.setDescription("Tarefa 1");
		taskToCreate.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskToCreate.setFinalDate(LocalDateTime.of(2023, 9, 25, 18, 0, 0));

		Task taskCreated = new Task();
		taskCreated.setId(1);
		taskCreated.setDescription("Tarefa 1");
		taskCreated.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskCreated.setFinalDate(LocalDateTime.of(2023, 9, 25, 18, 0, 0));

		// Given
		given(service.create(taskToCreate)).willReturn(taskCreated);

		// When
		var request = MockMvcRequestBuilders
				.post("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(taskToCreate));

		ResultActions response = mockMvc.perform(request);
		
		// Then
		response
		  //.andDo(print())
		  .andExpect(status().is(HttpStatus.CREATED.value()))
		  .andExpect(jsonPath("$.id", is(taskCreated.getId())));
	}

	@Test
	void testPostWithDataValidationFailure() throws Exception {
		Task taskToCreate = new Task();
		taskToCreate.setDescription("Tar");
		taskToCreate.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskToCreate.setFinalDate(null);

		// When
		var request = MockMvcRequestBuilders
				.post("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(taskToCreate));

		ResultActions response = mockMvc.perform(request);
		
		// Then
		response
		  //.andDo(print())
		  .andExpect(status().is(400))
		  .andExpect(jsonPath("$.reason[0]", containsString("Descrição deve ter pelo menos 5 caracteres")))
		  .andExpect(jsonPath("$.reason[1]", containsString("O campo 'final_date' é obrigatório")));
	}

	@Test
	void testGet() throws Exception {
		Task task = new Task();
		task.setId(1);
		task.setDescription("Tarefa 1");
		task.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		task.setFinalDate(LocalDateTime.of(2023, 9, 25, 18, 0, 0));

		List<Task> tasks = new ArrayList<>();
		tasks.add(task);

		// Given
		given(service.readAll()).willReturn(tasks);

		// When
		var request = MockMvcRequestBuilders.get("/tasks");
		ResultActions response = mockMvc.perform(request);

		// Then
		response
          //.andDo(print())
	      .andExpect(status().is(200))
	      .andExpect(jsonPath("$.size()", is(tasks.size())));
	}

	@Test
	void testPut() throws Exception {
		Task taskToUpdate = new Task();
		taskToUpdate.setId(1);
		taskToUpdate.setDescription("Tarefa 1");
		taskToUpdate.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskToUpdate.setFinalDate(LocalDateTime.of(2023, 9, 28, 18, 0, 0));

		Task taskUpdated = new Task();
		taskUpdated.setId(1);
		taskUpdated.setDescription("Tarefa 1");
		taskUpdated.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskUpdated.setFinalDate(LocalDateTime.of(2023, 9, 28, 18, 0, 0));

		// Given
		given(service.update(taskToUpdate)).willReturn(taskUpdated);

		// When
		var request = MockMvcRequestBuilders
				.put("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(taskToUpdate));

		ResultActions response = mockMvc.perform(request);

		// Then
		response
          //.andDo(print())
		  .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}

	@Test
	void testPutWithTaskDoesNotExist() throws Exception {
		Task task = new Task();
		task.setId(2);
		task.setDescription("Tarefa 2");
		task.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		task.setFinalDate(LocalDateTime.of(2023, 9, 28, 18, 0, 0));

		// Given
		Exception exception = new ResourceNotFoundException("Task does not exist!");
		given(service.update(task)).willThrow(exception);

		// When
		var request = MockMvcRequestBuilders
				.put("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(task));

		ResultActions response = mockMvc.perform(request);

		// Then
		response
		  //.andDo(print())
		  .andExpect(status().is(404))
		  .andExpect(jsonPath("$.reason", is("Task does not exist!")));
	}

	@Test
	void testDelete() throws Exception {
		int taskId = 1;
		
		Task taskDeleted = new Task();
		taskDeleted.setId(1);
		taskDeleted.setDescription("Tarefa 1");
		taskDeleted.setInitialDate(LocalDateTime.of(2023, 9, 25, 8, 0, 0));
		taskDeleted.setFinalDate(LocalDateTime.of(2023, 9, 28, 18, 0, 0));

		// Given
		given(service.delete(taskId)).willReturn(taskDeleted);

		// When
		var request = MockMvcRequestBuilders.delete("/tasks/{id}", taskId);

		ResultActions response = mockMvc.perform(request);

		// Then
		response
		  //.andDo(print())
		  .andExpect(status().isNoContent());
	}

	@Test
	void testDeleteWithTaskDoesNotExist() throws Exception {
		int taskId = 2;

		// Given
		Exception exception = new ResourceNotFoundException("Task does not exist!");
		given(service.delete(taskId)).willThrow(exception);

		// When
		var request = MockMvcRequestBuilders.delete("/tasks/{id}", taskId);

		ResultActions response = mockMvc.perform(request);

		// Then
		response
		  //.andDo(print())
		  .andExpect(status().is(404))
		  .andExpect(jsonPath("$.reason", is("Task does not exist!")));
	}

}
