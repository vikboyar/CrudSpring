package org.boyar.boyarkin;

import org.boyar.boyarkin.controller.TaskController;
import org.boyar.boyarkin.dto.TaskDto;
import org.boyar.boyarkin.exception.DbConstraintViolationException;
import org.boyar.boyarkin.exception.ResourceNotFoundException;
import org.boyar.boyarkin.exception.RusphoneViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class TaskController_deleteTest {
    private static final Long USER_ID = 100L;
    private static final Long TASK_ID = 11111111L;
    
    @Autowired
    TaskController taskController;

    TaskDto taskDto;

    @BeforeEach
    public void before() {
        taskDto = new TaskDto();
        taskDto.setTitle("TITLE!");
        taskDto.setDescription("DESCRIPTION!");
        taskDto.setCreatorId(USER_ID);
    }

    @AfterEach
    public void after() {
        taskDto = null;
    }

    @Test
    public void testDeleteTask() {
        //тест
        taskController.deleteTask(TASK_ID, USER_ID);
        //проверка
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> taskController.getTaskById(TASK_ID));
        assertEquals("Task not found", exception.getError());
    }

    @Test
    public void testDeleteTask_error() {
        //тест
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> taskController.deleteTask(TASK_ID, 200L));
        //проверка
        assertEquals("Task not found", exception.getError());
    }

}
