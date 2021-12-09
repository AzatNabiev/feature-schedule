package ru.waveaccess.features.unit.controllers;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.dto.UserDto;
import ru.waveaccess.features.enums.UserRoles;
import ru.waveaccess.features.forms.RegistrationForm;
import ru.waveaccess.features.forms.UserForm;
import ru.waveaccess.features.security.details.CustomUserDetails;
import ru.waveaccess.features.security.details.CustomUserDetailsService;
import ru.waveaccess.features.services.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName(value = "UserRestController is working when")
public class UserRestControllerTest extends DemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        UserForm userForm = UserForm.builder().userId("1").grade("DEVELOPER").build();
        RegistrationForm regForm = RegistrationForm.builder().name("AZAT").login("TEST@gmail.com").password("qwerty007").build();
        UserDto userDto = UserDto.builder().name("AZAT").login("TEST@gmail.com").build();
        when(userService.giveUserGrade(userForm)).thenReturn(userDto);
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setPassword("qwerty007");
        customUserDetails.setLogin("test@gmail.com");
        customUserDetails.setGrantedAuthorities(Collections.singleton(new SimpleGrantedAuthority(UserRoles.MANAGER.toString())));
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(customUserDetails);
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("addGradeToUser() is working")
    class addGradeToUser {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTg4NjgwMH0.QI31tgtpNksf37VaLNHA93sWZWc-oTkmHhXb7w6kIIUGv1n0z734_Z2ubuvkE6CD3rFT9GqJnuEMrfVbMKYEcg"})
        public void give_grade_to_user(String token) throws Exception {
            mockMvc.perform(post("/api/grade/status")
                            .with(SecurityMockMvcRequestPostProcessors.user("Azat").roles("MANAGER")
                                    .password("qwerty007").authorities(Collections.singletonList(new SimpleGrantedAuthority(UserRoles.MANAGER.toString()))))
                            .header("X-access-token", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"login\": \"TestDeveloper@gmail.com\",\n" +
                                    "  \"name\": \"Azat\",\n" +
                                    "  \"password\": \"qwerty007\"\n" +
                                    "}"))
                    .andDo(print())
                    .andExpect(status().isOk());

        }

        @Test
        public void access_denied_for_add_grade_to_user() throws Exception {
            mockMvc.perform(post("/api/grade/status"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

}
