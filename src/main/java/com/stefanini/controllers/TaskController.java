package com.stefanini.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.stefanini.dtos.TaskDto;
import com.stefanini.models.TaskModel;
import com.stefanini.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Cria um registro", description = "O status deve ser um desses: COMPLETED, NOT_STARTED, IN_PROGRESS", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Não pode haver um registro com o mesmo nome"),
    })
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Object> registerTask(@RequestBody @Validated(TaskDto.TaskView.TaskPost.class)
                                                   @JsonView(TaskDto.TaskView.TaskPost.class) TaskDto data) {
        try {
            TaskDto dataSaved = taskService.save(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(dataSaved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Recupera todos os registros", description = "sort por taskId", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registros não encontrados"),
    })
    @GetMapping("/getAllTasks")
    @Transactional
    public ResponseEntity<Page<TaskModel>> getAllTasks(@PageableDefault(page = 0, size = 50, sort = "taskId", direction = Sort.Direction.ASC)
                                                  Pageable pageable) {
        Page<TaskModel> taskModelPage = this.taskService.findAll(pageable);
        if (!taskModelPage.isEmpty()) {
            for (TaskModel task : taskModelPage.toList()) {
                task.add(linkTo(methodOn(TaskController.class).getOneTask(task.getTaskId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskModelPage);
    }

    @Operation(summary = "Recupera um registro", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Tarefa com esse Id não encontrada"),
    })
    @GetMapping("/getOneTask/{taskId}")
    @Transactional
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "taskId") UUID taskId) {
        return ResponseEntity.ok(this.taskService.findById(taskId));
    }

    @Operation(summary = "Atualiza um registro", description = "O status deve ser um desses: COMPLETED, NOT_STARTED, IN_PROGRESS", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Registro não encontrado"),
    })
    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Object> updateTask(@RequestBody @Validated(TaskDto.TaskView.TaskPut.class)
                                               @JsonView(TaskDto.TaskView.TaskPut.class) TaskDto data) {
        try {
            TaskDto dataUpdated = taskService.update(data);
            return ResponseEntity.status(HttpStatus.OK).body(dataUpdated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Deleta um registro", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "500", description = "Registro com esse Id não encontrado"),
    })
    @DeleteMapping("/deleteTask/{taskId}")
    @Transactional
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "taskId") UUID taskId) {
        try {
            this.taskService.delete(taskId);
            return ResponseEntity.status(HttpStatus.OK).body("Task successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
