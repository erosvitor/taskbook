package com.ctseducare.taskbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctseducare.taskbook.exception.response.ExceptionResponse;
import com.ctseducare.taskbook.model.Task;
import com.ctseducare.taskbook.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/tasks")
@Tag(name = "Task", description = "Registro, Listagem, Alteração e Remoção de tarefas")
public class TaskController {

	@GetMapping("/status")
	String status() {
		return "I am UP!";
	}
	
    @Autowired
    TaskService service;
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Registra uma tarefa.", description = "Após a tarefa ser registrada é retornado os dados da tarefa com o identificador preenchido.")
    @ApiResponses(
    	value = {
            @ApiResponse(responseCode = "200", description = "Dados da tarefa com o identificador preenchido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "400", description = "Erro informando problemas com os dados da requisição.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
        }
    )
    public ResponseEntity<Task> create(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(service.create(task));
    }
    
    @GetMapping(produces = "application/json")
    @Operation(summary = "Lista tarefas registradas.", description = "Listagem das tarefas previamente registradas.")
    @ApiResponses(
       	value = {
            @ApiResponse(responseCode = "200", description = "Lista contendo as tarefas.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Task.class))))
        }
    )
    public ResponseEntity<List<Task>> readAll() {
        return ResponseEntity.ok(service.readAll());
    }
    
    @PutMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Altera dados de uma tarefa.", description = "Após os dados serem alterados é retornado os dados da tarefa atualizados.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Dados da tarefa atualizados.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "400", description = "Erro informando problemas com os dados da requisição e/ou informando que a tarefa não existe.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
        }
    )
    public ResponseEntity<Task> update(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(service.update(task));
    }
    
    @DeleteMapping(path = "/{id}", produces="text/plain")
    @Operation(summary = "Remove uma tarefa.", description="Após a tarefa ser removida é retornado um status informando que a tarefa foi removida.")
    @ApiResponses(
      	value = {
            @ApiResponse(responseCode = "200", description = "Status informando que a tarefa com removida.", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Erro informando que a tarefa não existe.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
        }
    )
    public ResponseEntity<String> delete(@Parameter(description = "Identificador da tarefa.") @PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("Item excluído");
    }

}