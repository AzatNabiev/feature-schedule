package ru.waveaccess.features.unit.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.dto.UserDto;
import ru.waveaccess.features.forms.RegistrationForm;
import ru.waveaccess.features.services.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName(value = "RegRestController is working when")
class RegRestControllerTest extends DemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        RegistrationForm registrationForm = RegistrationForm.builder().name("TEST").login("TEST@gmail.com").password("QWERTY007").build();
        UserDto userDto = UserDto.builder().id(1L).login("TEST@gmail.com").name("TEST").build();
        when(userService.registerUser(registrationForm)).thenReturn(userDto);
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("registerUser() is working")
    class registerUser {
        @WithMockUser(username = "Azat", roles = {"DEVELOPER", "MANAGER", "TESTER"})
        @Test
        public void register_user() throws Exception {
            mockMvc.perform(post("/api/register")
                            .with(SecurityMockMvcRequestPostProcessors.user("Azat").roles("DEVELOPER"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"login\": \"TestDeveloper@gmail.com\",\n" +
                                    "  \"name\": \"Azat\",\n" +
                                    "  \"password\": \"qwerty007\"\n" + "}"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_register_user() throws Exception {
            mockMvc.perform(post("/api/register"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }
}