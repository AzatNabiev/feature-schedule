package ru.waveaccess.features.unit.controllers;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.dto.TaskDto;
import ru.waveaccess.features.forms.BugForm;
import ru.waveaccess.features.forms.TaskForm;
import ru.waveaccess.features.services.TaskService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName(value = "TaskRestController is working when")
public class TaskRestControllerTest extends DemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        List<TaskDto> taskDtoList = new ArrayList<>();
        TaskForm taskForm = TaskForm.builder().taskName("TEST").taskDescription("TEST").build();
        TaskDto taskDto = TaskDto.builder().name("TEST").description("TEST").build();
        taskDtoList.add(taskDto);
        when(taskService.addTaskToFeature(taskForm)).thenReturn(taskDto);
        when(taskService.getTaskById(1L)).thenReturn(taskDto);
        when(taskService.changeTaskStateForCheck(taskForm)).thenReturn(taskDto);
        when(taskService.getAllOpenTasks(taskForm)).thenReturn(taskDtoList);
        when(taskService.sendTaskToNext(taskForm)).thenReturn(taskDto);
        when(taskService.changeTaskState(1L, BugForm.builder().bugName("TEST").build())).thenReturn(taskDto);
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getAllOpenUserTask() is working")
    class getAllOpenUserTasks {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTg4NjgwMH0.QI31tgtpNksf37VaLNHA93sWZWc-oTkmHhXb7w6kIIUGv1n0z734_Z2ubuvkE6CD3rFT9GqJnuEMrfVbMKYEcg"})
        public void return_all_open_users_task(String token) throws Exception {
            mockMvc.perform(get("/api/task/opened")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"toUserById\": \"2\"\n" +
                                    "}")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_all_open_users_task() throws Exception {
            mockMvc.perform(get("/api/task/opened"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }


    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getTaskById() is working")
    class getTaskById {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTgwMDQwMH0.rbvegQmO8M_6qlgBcFm8yyqhiWnvLtgel8za5zH_yjfvK78v-Lyi2WEsc3HvUHnhyM0e4jRYK4Jpw-B1NCUXEw"})
        public void return_get_task_by_id(String token) throws Exception {
            mockMvc.perform(get("/api/task/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_get_task_by_id() throws Exception {
            mockMvc.perform(get("/api/task/1"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("addTaskToFeature() is working")
    class addTaskToFeature {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTgwMDQwMH0.rbvegQmO8M_6qlgBcFm8yyqhiWnvLtgel8za5zH_yjfvK78v-Lyi2WEsc3HvUHnhyM0e4jRYK4Jpw-B1NCUXEw"})
        public void add_task_to_feature(String token) throws Exception {
            mockMvc.perform(post("/api/task/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"featureId\": \"1\",\n" +
                                    "  \"taskName\": \"taskName\",\n" +
                                    "  \"taskDescription\": \"taskDescription\",\n" +
                                    "  \"toUserById\": \"2\"\n" +
                                    "\n" +
                                    "}")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_add_task_to_feature() throws Exception {
            mockMvc.perform(post("/api/task/"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("sendTaskToCheck() is working")
    class sendTaskToCheck {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTgwMDQwMH0.rbvegQmO8M_6qlgBcFm8yyqhiWnvLtgel8za5zH_yjfvK78v-Lyi2WEsc3HvUHnhyM0e4jRYK4Jpw-B1NCUXEw"})
        public void send_task_to_check(String token) throws Exception {
            mockMvc.perform(post("/api/task/solved")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"featureId\": \"1\",\n" +
                                    "  \"taskName\": \"taskName\",\n" +
                                    "  \"taskDescription\": \"taskDescription\",\n" +
                                    "  \"toUserById\": \"2\"\n" +
                                    "\n" +
                                    "}")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_send_task_to_check() throws Exception {
            mockMvc.perform(post("/api/task/solved"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("sendTaskToNext() is working")
    class sendTaskToNext {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTgwMDQwMH0.rbvegQmO8M_6qlgBcFm8yyqhiWnvLtgel8za5zH_yjfvK78v-Lyi2WEsc3HvUHnhyM0e4jRYK4Jpw-B1NCUXEw"})
        public void send_task_to_next(String token) throws Exception {
            mockMvc.perform(post("/api/task/solved/tester")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"taskId\": \"1\",\n" +
                                    "  \"taskName\": \"taskName\",\n" +
                                    "  \"taskRole\": \"RESOLVED\",\n" +
                                    "  \"toUserById\": \"3\"\n" +
                                    "}")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_send_task_to_next() throws Exception {
            mockMvc.perform(post("/api/task/solved/tester"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("reviewTask() is working")
    class reviewTask {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTgwMDQwMH0.rbvegQmO8M_6qlgBcFm8yyqhiWnvLtgel8za5zH_yjfvK78v-Lyi2WEsc3HvUHnhyM0e4jRYK4Jpw-B1NCUXEw"})
        public void review_task(String token) throws Exception {
            mockMvc.perform(post("/api/task/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("\n" +
                                    "{\n" +
                                    "  \"bugName\" : \"test bug\",\n" +
                                    "  \"description\" : \"test description\",\n" +
                                    "  \"status\":\"REJECTED\"\n" +
                                    "}")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_review_task() throws Exception {
            mockMvc.perform(post("/api/task/1"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

}
