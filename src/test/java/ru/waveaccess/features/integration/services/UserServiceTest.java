package ru.waveaccess.features.integration.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.dto.AuthDto;
import ru.waveaccess.features.dto.UserDto;
import ru.waveaccess.features.forms.AuthForm;
import ru.waveaccess.features.forms.RegistrationForm;
import ru.waveaccess.features.forms.UserForm;
import ru.waveaccess.features.models.User;
import ru.waveaccess.features.services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserServiceTest extends DemoApplicationTest {

    @Autowired
    private UserService userService;

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("registerUser() is working")
    class RegisterUser {
        @Test
        public void register_user() {
            RegistrationForm regForm = RegistrationForm.builder().login("test@gmail.com")
                    .name("test").password("qwerty007").build();
            UserDto expectedUserDto = UserDto.builder().id(4L).login("test@gmail.com").name("test").build();
            UserDto actualUserDto = userService.registerUser(regForm);
            assertEquals(expectedUserDto, actualUserDto);
        }

        @Test
        public void cant_register_user() {
            RegistrationForm regForm = RegistrationForm.builder().login("test@gmail.com")
                    .name("test").password("qwerty007").build();
            UserDto expectedUserDto = UserDto.builder().id(4L).name("test").build();
            UserDto actualUserDto = userService.registerUser(regForm);
            assertNotEquals(expectedUserDto, actualUserDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("authenticateUser() is working")
    class AuthenticateUser {
        @Test
        public void authenticate_user() {
            AuthForm authForm = AuthForm.builder().login("test@gmail.com").password("qwerty007").build();
            AuthDto actualAuthDto = userService.authenticateUser(authForm);
            AuthDto expectedAuthDto = AuthDto.builder().token(actualAuthDto.getToken()).build();
            assertEquals(expectedAuthDto, actualAuthDto);
        }

        @Test
        public void cant_authenticate_user() {
            AuthForm authForm = AuthForm.builder().login("test@gmail.com").password("qwerty007").build();
            AuthDto actualAuthDto = userService.authenticateUser(authForm);
            AuthDto expectedAuthDto = AuthDto.builder().token("1111").build();
            assertNotEquals(expectedAuthDto, actualAuthDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("giveUserGrade() is working")
    class GiveUserGrade {
        @Test
        public void give_user_grade() {
            UserForm userForm = UserForm.builder().userId("3").grade("TESTER").build();
            UserDto expectedUserDto = UserDto.builder().id(3L).name("Mark")
                    .login("tester@gmail.com").build();
            UserDto actualUserDto = userService.giveUserGrade(userForm);
            assertEquals(expectedUserDto, actualUserDto);
        }

        @Test
        public void cant_give_user_grade() {
            UserForm userForm = UserForm.builder().userId("3").grade("TESTER").build();
            UserDto expectedUserDto = UserDto.builder().id(1L).build();
            UserDto actualUserDto = userService.giveUserGrade(userForm);
            assertNotEquals(expectedUserDto, actualUserDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("findUserByLogin() is working")
    class FindUserByLogin {
        @Test
        public void find_user_by_login() {
            User expectedUser = User.builder().id(1L).name("Schmidt").login("test@gmail.com")
                    .password("$2a$10$/6o2rXVzHxin/CcCfYlyYOn.wWJj6zfNypkxteWpVvKFORT56p8/6").build();
            User actualUser = userService.findUserByLogin("test@gmail.com");
            assertEquals(expectedUser, actualUser);
        }

        @Test
        public void cant_find_user_by_login() {
            User expectedUser = User.builder().id(2L).name("Schmidt").login("test@gmail.com")
                    .password("$2a$10$/6o2rXVzHxin/CcCfYlyYOn.wWJj6zfNypkxteWpVvKFORT56p8/6").build();
            User actualUser = userService.findUserByLogin("test@gmail.com");
            assertNotEquals(expectedUser, actualUser);
        }
    }
}
