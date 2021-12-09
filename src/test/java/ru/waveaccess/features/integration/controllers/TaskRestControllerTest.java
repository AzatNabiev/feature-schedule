package ru.waveaccess.features.integration.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.dto.TaskDto;
import ru.waveaccess.features.dto.UserDto;
import ru.waveaccess.features.enums.TaskRoles;
import ru.waveaccess.features.forms.TaskForm;
import ru.waveaccess.features.services.TaskService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskRestControllerTest extends DemoApplicationTest {
    @Autowired
    private TaskService taskService;

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getTaskByParams() endpoint test")
    class AddFeature {
        @Test
        public void get_task_by_all_params() {
            // data init
            TaskForm taskForm = TaskForm.builder().taskName("test").toUserById("2").taskState("OPEN").build();
            UserDto userDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("test").description("test").taskRoles(TaskRoles.OPEN).user(userDto).build();

            // executing
            TaskDto actualTaskDto = taskService.getTaskPyParams(taskForm);

            // asserting result
            assertEquals(expectedTaskDto, actualTaskDto);
        }

        @Test
        public void get_task_by_user() {
            // data init
            TaskForm taskForm = TaskForm.builder().taskName("test").toUserById("2").taskState("OPEN").build();
            UserDto userDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("test").description("test").taskRoles(TaskRoles.OPEN).user(userDto).build();

            // executing
            TaskDto actualTaskDto = taskService.getTaskPyParams(taskForm);

            // asserting result
            assertEquals(expectedTaskDto, actualTaskDto);
        }

        @Test
        public void get_task_by_name() {
            // data init
            TaskForm taskForm = TaskForm.builder().taskName("test").build();
            UserDto userDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("test").description("test").taskRoles(TaskRoles.OPEN).user(userDto).build();

            // executing
            TaskDto actualTaskDto = taskService.getTaskPyParams(taskForm);

            // asserting result
            assertEquals(expectedTaskDto, actualTaskDto);
        }

        @Test
        public void get_task_by_task_state() {
            // data init
            TaskForm taskForm = TaskForm.builder().taskState("OPEN").build();
            UserDto userDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("test").description("test").taskRoles(TaskRoles.OPEN).user(userDto).build();

            // executing
            TaskDto actualTaskDto = taskService.getTaskPyParams(taskForm);

            // asserting result
            assertEquals(expectedTaskDto, actualTaskDto);
        }
    }
}
