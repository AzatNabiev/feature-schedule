package ru.waveaccess.features.integration.repositories;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.enums.TaskRoles;
import ru.waveaccess.features.models.Task;
import ru.waveaccess.features.repositories.TaskJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TaskJpaRepositoryTest extends DemoApplicationTest {

    @Autowired
    private TaskJpaRepository taskJpaRepository;

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getAllByTaskRoleAndUser() is working")
    class getAllByTaskRoleAndUser {
        @Test
        public void get_all_by_task_and_user() {
            List<Task> actualTasks;
            Optional<List<Task>> optionalTasks = taskJpaRepository.getAllByTaskRoleAndUser("OPEN", 2L);
            actualTasks = optionalTasks.orElseGet(ArrayList::new);
            List<Task> expectedTasks = new ArrayList<>();
            expectedTasks.add(Task.builder().id(1L).name("test").description("test").taskRole(TaskRoles.OPEN).build());
            assertEquals(expectedTasks, actualTasks);
        }

        @Test
        public void cant_get_all_by_task_and_user() {
            List<Task> actualTasks;
            Optional<List<Task>> optionalTasks = taskJpaRepository.getAllByTaskRoleAndUser("OPN", 2L);
            actualTasks = optionalTasks.orElseGet(ArrayList::new);
            List<Task> expectedTasks = new ArrayList<>();
            expectedTasks.add(Task.builder().id(1L).name("test").description("test").taskRole(TaskRoles.OPEN).build());
            assertNotEquals(expectedTasks, actualTasks);
        }
    }
}
