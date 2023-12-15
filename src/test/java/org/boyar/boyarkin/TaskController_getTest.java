package org.boyar.boyarkin;

import org.boyar.boyarkin.controller.TaskController;
import org.boyar.boyarkin.dto.TaskDto;
import org.boyar.boyarkin.exception.DbConstraintViolationException;
import org.boyar.boyarkin.exception.ResourceNotFoundException;
import org.boyar.boyarkin.exception.RusphoneViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(statements = {
        "INSERT INTO user_user (id, fio) VALUES (100, 'Иванов Иван Иваныч')",
        "INSERT INTO task (id, title, description, creator_id, created, updated) VALUES (11111111, 'title_test', 'some descr', 100, null, null)"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(statements = {"DELETE FROM TASK", "DELETE FROM USER_USER"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class TaskController_getTest {

    @Autowired
    TaskController taskController;
    
    @Test
    public void testGetTaskById() {
        //тест
        TaskDto task = taskController.getTaskById(11111111L);
        // проверка
        assertEquals("some descr", task.getDescription());
    }

    @Test
    public void testGetTaskById_error() {
        //тест
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> taskController.getTaskById(100000L));
        // проверка
        assertEquals("Task not found", exception.getError());
    }

}
