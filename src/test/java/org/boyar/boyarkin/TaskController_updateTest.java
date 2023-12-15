package org.boyar.boyarkin;

import org.boyar.boyarkin.controller.TaskController;
import org.boyar.boyarkin.dto.TaskDto;
import org.boyar.boyarkin.exception.DbConstraintViolationException;
import org.boyar.boyarkin.exception.ResourceNotFoundException;
import org.boyar.boyarkin.exception.UnexpectedException;
import org.boyar.boyarkin.exception.UserConflictException;
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
        "INSERT INTO task (id, title, description, creator_id, created, updated) VALUES (11111111, 'title_test', 'some descr', 100, null, null)",
        "INSERT INTO task (id, title, description, creator_id, created, updated) VALUES (2222222, 'title_test2', 'some descr', 100, null, null)"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(statements = {"DELETE FROM TASK", "DELETE FROM USER_USER"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TaskController_updateTest {
    private static final Long USER_ID = 100L;
    private static final Long TASK_ID = 11111111L;

    @Autowired
    TaskController taskController;

    TaskDto taskDto;

    @BeforeEach
    public void before() {
        taskDto = new TaskDto();
        taskDto.setId(TASK_ID);
        taskDto.setTitle("TITLE!");
        taskDto.setDescription("DESCRIPTION!");
        taskDto.setCreatorId(USER_ID);
    }

    @AfterEach
    public void after() {
        taskDto = null;
    }

    @Test
    public void testUpdateTask() {
        //тест
        TaskDto taskNew = taskController.updateTask(taskDto, USER_ID);
        //проверка
        TaskDto taskCheck = taskController.getTaskById(taskNew.getId());
        assertEquals("DESCRIPTION!", taskCheck.getDescription());
    }

    @Test
    public void testUpdateTask_error() {
        //тест
        UserConflictException exception = assertThrows(UserConflictException.class, () -> taskController.updateTask(taskDto, 200L));
        //проверка
        assertEquals("Creator and user are not equal", exception.getError());
    }

    @Test
    public void testUpdateTask_errorUniqueConstraint() {
        taskDto.setTitle("title_test2");
        //тест
        DbConstraintViolationException exception = assertThrows(DbConstraintViolationException.class, () -> taskController.updateTask(taskDto, USER_ID));
        //проверка
        assertEquals("Unique constraint violation", exception.getError());
    }

    @Test
    public void testUpdateTask_errorDescriptionTooLongConstraint() {
        String TOO_LONG = "ksfkjsakjbkasjdbf sdbvasdbvasjbdvkas jdbvkjsdacaslkjkjashkasjdksjdbvks askdjfaskjdfkasjdf asdkjfh kasjdhfkjas";
        taskDto.setDescription(TOO_LONG);
        //тест
        DbConstraintViolationException exception = assertThrows(DbConstraintViolationException.class, () -> taskController.updateTask(taskDto, USER_ID));
        //проверка
        assertEquals("Too long value", exception.getError());
    }

}
