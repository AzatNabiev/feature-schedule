package ru.waveaccess.features.unit.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.dto.TaskDto;
import ru.waveaccess.features.dto.UserDto;
import ru.waveaccess.features.enums.BugRoles;
import ru.waveaccess.features.enums.TaskRoles;
import ru.waveaccess.features.forms.BugForm;
import ru.waveaccess.features.forms.TaskForm;
import ru.waveaccess.features.models.Feature;
import ru.waveaccess.features.models.Task;
import ru.waveaccess.features.models.User;
import ru.waveaccess.features.repositories.FeatureJpaRepository;
import ru.waveaccess.features.repositories.TaskJpaRepository;
import ru.waveaccess.features.repositories.UserJpaRepository;
import ru.waveaccess.features.services.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName(value = "TaskServiceImpl is working")
class TaskServiceTest extends DemoApplicationTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskJpaRepository taskJpaRepository;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @MockBean
    private FeatureJpaRepository featureJpaRepository;


    @BeforeEach
    void setUp() {
        List<Task> taskList = new ArrayList<>();
        List<Feature> features = new ArrayList<>();
        Task task = Task.builder().id(1L).name("TEST").taskRole(TaskRoles.OPEN).build();
        Feature feature = Feature.builder().id(1L).build();
        task.setFeature(feature);
        User user = User.builder().id(1L).taskList(taskList).featureList(features).build();
        UserDto userDto = UserDto.builder().id(1L).build();

        TaskDto taskDto = TaskDto.builder().id(1L).user(userDto).build();
        task.setUser(user);
        task.setFeature(feature);
        when(taskJpaRepository.findById(1L)).thenReturn(Optional.of(task));

        when(taskJpaRepository.save(task)).thenReturn(task);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        when(taskJpaRepository.getAllByTaskRoleAndUser("Open", 1L)).thenReturn(Optional.of(tasks));
        when(featureJpaRepository.findById(1L)).thenReturn(Optional.of(feature));
        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("sendTaskToNext() is working")
    class sendTaskToNext {
        @Test
        public void send_task_to_next() {
            // data init
            TaskForm taskForm = TaskForm.builder().toUserById("1").taskId("1").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("TEST").user(UserDto.builder().id(1L).build())
                    .taskRoles(TaskRoles.RESOLVED).build();

            // executing
            TaskDto taskDto = taskService.sendTaskToNext(taskForm);

            // asserting result
            assertEquals(expectedTaskDto, taskDto);
        }
    }


    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getAllOpenTasks() is working")
    class getAllOpenTasks {
        @Test
        public void get_all_open_tasks() {
            // data init
            TaskForm taskForm = TaskForm.builder().toUserById("1").build();
            List<TaskDto> expectedTaskDtos = new ArrayList<>();
            TaskDto taskDto = TaskDto.builder().id(1L).name("TEST").taskRoles(TaskRoles.IN_PROGRESS)
                    .user(UserDto.builder().id(1L).build()).build();
            expectedTaskDtos.add(taskDto);

            // executing
            List<TaskDto> actualTaskDtos = taskService.getAllOpenTasks(taskForm);

            // asserting result
            assertEquals(expectedTaskDtos, actualTaskDtos);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getTaskById() is working")
    class getTaskById {
        @Test
        public void get_task_by_id() {
            // data init
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("TEST")
                    .taskRoles(TaskRoles.OPEN).user(UserDto.builder().id(1L).build()).build();

            // executing
            TaskDto actualTaskDto = taskService.getTaskById(1L);

            // asserting result
            assertEquals(expectedTaskDto, actualTaskDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("changeTaskState() is working")
    class changeTaskState {
        @Test
        public void change_task_state() {
            // data init
            BugForm bugForm = BugForm.builder().taskId("1").status(BugRoles.ACCEPTED.toString()).build();
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("TEST").user(UserDto.builder().id(1L).build())
                    .taskRoles(TaskRoles.COMPLETED).build();

            // executing
            TaskDto actualTaskDto = taskService.changeTaskState(1L, bugForm);

            // asserting result
            assertEquals(expectedTaskDto, actualTaskDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("addTaskToFeature() is working")
    class addTaskToFeature {
        @Test
        public void add_task_to_feature() {
            // data init
            TaskForm taskForm = TaskForm.builder().toUserById("1").featureId("1").build();
            TaskDto expectedTaskDto = TaskDto.builder().taskRoles(TaskRoles.OPEN).user(UserDto.builder().id(1L).build()).build();

            // executing
            TaskDto actualTaskDto = taskService.addTaskToFeature(taskForm);

            // asserting result
            assertEquals(expectedTaskDto, actualTaskDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("changeTaskStateForCheck() is working")
    class changeTaskStateForCheck {
        @Test
        public void change_task_state_for_check() {
            // data init
            TaskForm taskForm = TaskForm.builder().taskId("1").build();
            TaskDto expectedTaskDto = TaskDto.builder().id(1L).name("TEST").taskRoles(TaskRoles.RESOLVED)
                    .build();

            // executing
            TaskDto actualTaskDto = taskService.changeTaskStateForCheck(taskForm);

            // asserting result
            assertEquals(expectedTaskDto, actualTaskDto);
        }
    }
}