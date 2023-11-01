package com.ctseducare.taskbook.exception.handle;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ctseducare.taskbook.exception.ResourceNotFoundException;
import com.ctseducare.taskbook.exception.response.ExceptionResponse;

@ControllerAdvice
public class ResponseEntityExceptionHandlerCustom extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleResourceNotFoundExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());

		/*
		 *  A ordem das mensagens de erro referentes a validação dos campos é aleatória. Isto dificulta a criação de testes unitários, uma vez que as mensagens
		 *  são retornadas num vetor de strings e nos testes temos que pegar posição por posição do vetor e validar individualmente.
		 *  
		 *  Exemplo:
		 *  
		 *    ResultActions response = mockMvc.perform(request);
		 *    response
		 *      .andExpect(status().is(400))
		 *      .andExpect(jsonPath("$.reason[0]", containsString("Descrição deve ter pelo menos 5 caracteres")))
		 *      .andExpect(jsonPath("$.reason[1]", containsString("O campo 'final_date' é obrigatório")));
		 *      
		 */
		Collections.sort(errors);
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("error", status.value());
		body.put("reason", errors);

		return new ResponseEntity<>(body, headers, status);
	}

}