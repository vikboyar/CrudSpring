package org.boyar.boyarkin.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.boyar.boyarkin.dao.TaskRepository;
import org.boyar.boyarkin.dto.TaskDto;
import org.boyar.boyarkin.exception.DbConstraintViolationException;
import org.boyar.boyarkin.exception.UnexpectedException;
import org.boyar.boyarkin.model.Task;
import org.boyar.boyarkin.exception.ResourceNotFoundException;
import org.boyar.boyarkin.utils.RusPhoneValidation;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.boyar.boyarkin.utils.ValidationUtils.areNotEquals;
import static org.boyar.boyarkin.utils.ValidationUtils.ifNull;

@Service
@RequiredArgsConstructor
public class TaskService {

    public static final String TASK_NOT_FOUND = "Task not found";
    private final TaskRepository taskRepository;

    @SneakyThrows
    public TaskDto getTaskById(@NonNull Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TASK_NOT_FOUND));
        TaskDto taskDto = new ModelMapper().map(task, TaskDto.class);
        return taskDto;
    }

    @SneakyThrows
    public TaskDto createTaskForUser(@NonNull TaskDto taskDto, @NonNull Long userId) {
        ifNull(taskDto.getTitle(), "taskDto.title");
        ifNull(taskDto.getCreatorId(), "taskDto.creatorId");
        areNotEquals(taskDto.getCreatorId(), userId, "Creator and user are not equal");
        RusPhoneValidation.doesNotContainRusPhone(taskDto.getDescription());

        Task task = new ModelMapper().map(taskDto, Task.class);
        try {
            return new ModelMapper().map(taskRepository.save(task), TaskDto.class);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("Unique")) {
                throw new DbConstraintViolationException("Unique constraint violation");
            } else if (e.getMessage().contains("CHARACTER VARYING")) {
                throw new DbConstraintViolationException("Too long value");
            }
        }

        throw new UnexpectedException("Какое то неожиданние исключение");
    }

    @SneakyThrows
    public TaskDto updateTaskForUser(@NonNull TaskDto taskDto, @NonNull Long userId) {
        ifNull(taskDto.getTitle(), "taskDto.title");
        ifNull(taskDto.getId(), "taskDto.id");
        ifNull(taskDto.getCreatorId(), "taskDto.creatorId");
        areNotEquals(taskDto.getCreatorId(), userId, "Creator and user are not equal");
        RusPhoneValidation.doesNotContainRusPhone(taskDto.getDescription());

        Task task = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(TASK_NOT_FOUND));
        taskDto.setUpdated(new Date());
        try {
            return new ModelMapper().map(taskRepository.save(new ModelMapper().map(taskDto, Task.class)), TaskDto.class);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("Unique")) {
                throw new DbConstraintViolationException("Unique constraint violation");
            } else if (e.getMessage().contains("CHARACTER VARYING")) {
                throw new DbConstraintViolationException("Too long value");
            }
        }
        throw new UnexpectedException("Какое то неожиданние исключение");
    }

    @SneakyThrows
    public void deleteTaskByIdForUser(@NonNull Long id, @NonNull Long userId) {
        Task task = taskRepository.getTaskByIdAndCreatorId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(TASK_NOT_FOUND));
        taskRepository.delete(task);
    }
}
