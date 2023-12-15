package org.boyar.boyarkin.controller;

import lombok.RequiredArgsConstructor;
import org.boyar.boyarkin.dto.TaskDto;
import org.boyar.boyarkin.service.TaskService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping(path = "id/{id}", produces = APPLICATION_JSON_VALUE)
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TaskDto createTask(@RequestBody TaskDto taskDto,
                           @RequestParam(value = "user_id", required = true) Long userId) {
        return taskService.createTaskForUser(taskDto, userId);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody TaskDto taskDto,
                           @RequestParam(value = "user_id", required = true) Long userId) {
        return taskService.updateTaskForUser(taskDto, userId);
    }

    @DeleteMapping(path = "id/{id}")
    public void deleteTask(@PathVariable Long id,
                           @RequestParam(value = "user_id" , required = true) Long userId) {
        taskService.deleteTaskByIdForUser(id, userId);
    }
}
