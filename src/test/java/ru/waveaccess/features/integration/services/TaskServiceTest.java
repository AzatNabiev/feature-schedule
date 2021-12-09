package ru.waveaccess.features.integration.services;

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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TaskServiceTest extends DemoApplicationTest {

    @Autowired
    private TaskService taskService;

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("sendTaskToNext() is working")
    class SendTaskToNext {
        @Test
        public void send_task_to_next() {
            TaskForm taskForm = TaskForm.builder().taskId("1").toUserById("3").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("test").description("test")
                    .taskRoles(TaskRoles.RESOLVED).user(UserDto.builder().id(3L).login("tester@gmail.com")
                            .name("Mark").build()).build();
            TaskDto actualTaskDto = taskService.sendTaskToNext(taskForm);
            assertEquals(expectedTaskDto, actualTaskDto);
        }

        @Test
        public void cant_send_task_to_next() {
            TaskForm taskForm = TaskForm.builder().taskId("1").toUserById("3").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("test").description("test")
                    .taskRoles(TaskRoles.RESOLVED).user(UserDto.builder().id(3L).login("tester@gmail.com")
                            .name("Mack").build()).build();
            TaskDto actualTaskDto = taskService.sendTaskToNext(taskForm);
            assertNotEquals(expectedTaskDto, actualTaskDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("addTaskToFeature() is working")
    class AddTaskToFeature {
        @Test
        public void add_task_to_feature() {
            TaskForm taskForm = TaskForm.builder().toUserById("1").featureId("1").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(2L).taskRoles(TaskRoles.OPEN).user(UserDto
                    .builder().id(1L).login("test@gmail.com").name("Schmidt").build()).build();
            TaskDto actualTaskDto = taskService.addTaskToFeature(taskForm);
            assertEquals(expectedTaskDto, actualTaskDto);
        }

        @Test
        public void cant_add_task_to_feature() {
            TaskForm taskForm = TaskForm.builder().toUserById("1").featureId("1").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(2L).taskRoles(TaskRoles.OPEN).user(UserDto
                    .builder().id(1L).login("test@gmail.com").name("Schmit").build()).build();
            TaskDto actualTaskDto = taskService.addTaskToFeature(taskForm);
            assertNotEquals(expectedTaskDto, actualTaskDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getAllOpenTasks() is working")
    class GetAllOpenTasks {
        @Test
        public void get_all_open_tasks() {
            TaskForm taskForm = TaskForm.builder().toUserById("1").build();
            List<TaskDto> expectedTaskDtoList = new ArrayList<>();
            List<TaskDto> actualTaskDtoList = taskService.getAllOpenTasks(taskForm);
            assertEquals(expectedTaskDtoList, actualTaskDtoList);
        }

        @Test
        public void cant_get_all_open_tasks() {
            TaskForm taskForm = TaskForm.builder().toUserById("1").build();
            List<TaskDto> expectedTaskDtoList = new ArrayList<>();
            expectedTaskDtoList.add(TaskDto.builder().id(1L).build());
            List<TaskDto> actualTaskDtoList = taskService.getAllOpenTasks(taskForm);
            assertNotEquals(expectedTaskDtoList, actualTaskDtoList);
        }
    }
}
