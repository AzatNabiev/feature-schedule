package ru.waveaccess.features.integration.repositories;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.models.User;
import ru.waveaccess.features.repositories.UserJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class UserJpaRepositoryTest extends DemoApplicationTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("findByLogin() is working")
    class findByLogin {
        @Test
        public void find_by_login_is_works() {
            Optional<User> user = userJpaRepository.findByLogin("test@gmail.com");
            User userExpected = User.builder().id(1L).login("test@gmail.com").password("$2a$10$/6o2rXVzHxin/CcCfYlyYOn.wWJj6zfNypkxteWpVvKFORT56p8/6").name("Schmidt").build();
            assertEquals(userExpected, user.get());
        }

        @Test
        public void cant_find_by_login() {
            User userActual;
            Optional<User> userOptional = userJpaRepository.findByLogin("testgmail.com");
            userActual = userOptional.orElseGet(() -> User.builder().name("TEST").build());
            User userExpected = User.builder().id(1L).login("test@gmail.com").password("$2a$10$/6o2rXVzHxin/CcCfYlyYOn.wWJj6zfNypkxteWpVvKFORT56p8/6").name("Schmidt").build();
            assertNotEquals(userExpected, userActual);
        }
    }
}
