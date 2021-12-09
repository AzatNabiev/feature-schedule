package ru.waveaccess.features.unit.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.dto.AuthDto;
import ru.waveaccess.features.dto.UserDto;
import ru.waveaccess.features.enums.UserRoles;
import ru.waveaccess.features.forms.AuthForm;
import ru.waveaccess.features.forms.RegistrationForm;
import ru.waveaccess.features.forms.UserForm;
import ru.waveaccess.features.models.Role;
import ru.waveaccess.features.models.User;
import ru.waveaccess.features.repositories.RoleJpaRepository;
import ru.waveaccess.features.repositories.UserJpaRepository;
import ru.waveaccess.features.security.jwt.JwtProvider;
import ru.waveaccess.features.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName(value = "UserServiceImpl is working")
public class UserServiceTest extends DemoApplicationTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @MockBean
    private RoleJpaRepository roleJpaRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtProvider jwtProvider;

    @BeforeEach
    public void setUp() {
        User user = User.builder().name("TEST").login("test@gmail.com").password("qwerty007").build();
        User userAfterSaving = User.builder().id(1L).name("TEST").login("test@gmail.com").password("qwerty007").build();
        UserDto userDto = UserDto.builder().id(1L).name("TEST").login("test@gmail.com").build();
        when(userJpaRepository.save(user)).thenReturn(userAfterSaving);
        when(passwordEncoder.encode("qwerty007")).thenReturn("qwerty007");
        AuthForm authForm = AuthForm.builder().login("test@gmail.com").password("qwerty007").build();
        when(userJpaRepository.findByLogin("test@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authForm.getPassword(), user.getPassword())).thenReturn(Boolean.TRUE);
        when(jwtProvider.generateToken("test@gmail.com")).thenReturn("testtest");
        Role role = Role.builder().id(2).userRoles(UserRoles.DEVELOPER).build();
        when(roleJpaRepository.findByUserRoles("DEVELOPER")).thenReturn(Optional.of(role));
        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("registerUser() is working")
    class registerUser {
        @Test
        public void register_user() {
            // data init
            RegistrationForm regForm = RegistrationForm.builder().name("TEST").login("test@gmail.com")
                    .password("qwerty007").build();
            UserDto userDto = UserDto.builder().name("TEST").login("test@gmail.com").build();

            // executing
            UserDto userDtoAfterRegister = userService.registerUser(regForm);

            // asserting result
            assertEquals(userDtoAfterRegister, userDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("authenticateUser() is working")
    class AuthUser {
        @Test
        public void auth_user() {
            // data init
            AuthForm authForm = AuthForm.builder().login("test@gmail.com").password("qwerty007").build();
            AuthDto authExpectedDto = AuthDto.builder().token("testtest").build();

            // executing
            AuthDto authActualDto = userService.authenticateUser(authForm);

            // asserting  result
            assertEquals(authExpectedDto, authActualDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("giveUserGrade() is working")
    class giveGradeToUser {
        @Test
        public void giving_grade_to_user() {
            // data init
            UserDto expectedUserDto = UserDto.builder().name("TEST").login("test@gmail.com").build();
            UserForm userForm = UserForm.builder().grade("DEVELOPER").userId("1").build();

            // executing
            UserDto actualUserDto = userService.giveUserGrade(userForm);

            // asserting result
            assertEquals(expectedUserDto, actualUserDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("findUserByLogin() is working")
    class findUserByLogin {
        @Test
        public void finding_user_by_login() {
            // data init
            User expectedUser = User.builder().name("TEST").login("test@gmail.com").password("qwerty007").build();

            // executing
            Optional<User> actualUser = userJpaRepository.findByLogin("test@gmail.com");

            // asserting result
            assertEquals(expectedUser, actualUser.get());
        }
    }

}
