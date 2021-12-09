package ru.waveaccess.features.integration.repositories;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.enums.UserRoles;
import ru.waveaccess.features.models.Role;
import ru.waveaccess.features.repositories.RoleJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RoleJpaRepositoryTet extends DemoApplicationTest {

    @Autowired
    private RoleJpaRepository roleJpaRepository;

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("findByUserRoles() is working")
    class findByUserRoles {
        @Test
        public void find_by_user_roles_works() {
            Role actualRole;
            Role expectedRole = Role.builder().id(1).userRoles(UserRoles.MANAGER).build();
            Optional<Role> roleOptional = roleJpaRepository.findByUserRoles("MANAGER");
            actualRole = roleOptional.orElseGet(() -> Role.builder().id(1).build());
            assertEquals(expectedRole, actualRole);
        }

        @Test
        public void cant_find_by_user_roles() {
            Role actualRole;
            Role expectedRole = Role.builder().id(1).userRoles(UserRoles.DEVELOPER).build();
            Optional<Role> roleOptional = roleJpaRepository.findByUserRoles("MANAGER");
            actualRole = roleOptional.orElseGet(() -> Role.builder().id(1).build());
            assertNotEquals(expectedRole, actualRole);
        }
    }
}
