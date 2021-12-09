package ru.waveaccess.features.integration.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.dto.FeatureDto;
import ru.waveaccess.features.dto.UserDto;
import ru.waveaccess.features.enums.FeatureRoles;
import ru.waveaccess.features.enums.TaskRoles;
import ru.waveaccess.features.forms.FeatureForm;
import ru.waveaccess.features.models.Task;
import ru.waveaccess.features.services.FeatureService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FeatureServiceTest extends DemoApplicationTest {

    @Autowired
    private FeatureService featureService;

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("addFeature() is working")
    class AddFeature {
        @Test
        public void add_feature() {
            FeatureForm featureForm = FeatureForm.builder().featureName("TEST")
                    .userId("2").featureDescription("TEST").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().id(2L).description("TEST")
                    .name("TEST").featureRoles(FeatureRoles.OPEN).build();
            FeatureDto actualFeatureDto = featureService.addFeature(featureForm);
            assertEquals(expectedFeatureDto, actualFeatureDto);
        }

        @Test
        public void cant_add_feature() {
            FeatureForm featureForm = FeatureForm.builder().userId("3")
                    .featureDescription("TEST").featureName("TEST").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().description("TEST")
                    .name("TEST").featureRoles(FeatureRoles.OPEN).build();
            FeatureDto actualFeatureDto = featureService.addFeature(featureForm);
            assertNotEquals(expectedFeatureDto, actualFeatureDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getAllFeatures() is working")
    class GetAllFeatures {
        @Test
        public void get_all_features() {
            List<FeatureDto> expectedFeatureDtos = new ArrayList<>();
            List<Task> expectedTasks = new ArrayList<>();
            Task task = Task.builder().id(1L).taskRole(TaskRoles.OPEN).name("test").description("test").build();
            expectedTasks.add(task);
            UserDto expectedUserDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().id(1L).user(expectedUserDto)
                    .featureRoles(FeatureRoles.OPEN).name("TEST").tasks(expectedTasks).description("TEST").build();
            expectedFeatureDtos.add(expectedFeatureDto);
            List<FeatureDto> actualFeatureDtos = featureService.getAllFeatures();
            assertEquals(expectedFeatureDtos, actualFeatureDtos);
        }

        @Test
        public void cant_get_all_features() {
            List<FeatureDto> expectedFeatureDtos = new ArrayList<>();
            List<Task> expectedTasks = new ArrayList<>();
            Task task = Task.builder().id(1L).taskRole(TaskRoles.OPEN).name("test").description("test").build();
            expectedTasks.add(task);
            UserDto expectedUserDto = UserDto.builder().id(1L).login("developer@gmail.com").name("Azat").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().id(1L).user(expectedUserDto)
                    .featureRoles(FeatureRoles.OPEN).name("TEST").tasks(expectedTasks).description("TEST").build();
            expectedFeatureDtos.add(expectedFeatureDto);
            List<FeatureDto> actualFeatureDtos = featureService.getAllFeatures();
            assertNotEquals(expectedFeatureDtos, actualFeatureDtos);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("addUserToFeature() is working")
    class AddUserToFeature {
        @Test
        public void add_user_to_feature() {
            List<Task> expectedTasks = new ArrayList<>();
            Task expectedTask = Task.builder().id(1L).taskRole(TaskRoles.OPEN).name("test").description("test").build();
            expectedTasks.add(expectedTask);
            FeatureForm featureForm = FeatureForm.builder().featureId("1").userId("2").build();
            UserDto expectedUserDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().id(1L).user(expectedUserDto).name("TEST")
                    .tasks(expectedTasks).description("TEST").featureRoles(FeatureRoles.IN_PROGRESS).build();
            FeatureDto actualFeatureDto = featureService.addUserToFeature(featureForm);
            assertEquals(expectedFeatureDto, actualFeatureDto);
        }

        @Test
        public void cant_add_user_to_feature() {
            List<Task> expectedTasks = new ArrayList<>();
            Task expectedTask = Task.builder().id(1L).taskRole(TaskRoles.OPEN).name("test").description("test").build();
            expectedTasks.add(expectedTask);
            FeatureForm featureForm = FeatureForm.builder().featureId("1").userId("2").build();
            UserDto expectedUserDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().id(1L).user(expectedUserDto).name("TEST")
                    .tasks(expectedTasks).featureRoles(FeatureRoles.IN_PROGRESS).build();
            FeatureDto actualFeatureDto = featureService.addUserToFeature(featureForm);
            assertNotEquals(expectedFeatureDto, actualFeatureDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getFeatureById() is working")
    class GetFeatureById {
        @Test
        public void get_feature_by_id() {
            List<Task> expectedTasks = new ArrayList<>();
            Task expectedTask = Task.builder().id(1L).taskRole(TaskRoles.OPEN).name("test").description("test").build();
            expectedTasks.add(expectedTask);
            UserDto expectedUserDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().id(1L).name("TEST").description("TEST")
                    .featureRoles(FeatureRoles.OPEN).tasks(expectedTasks).user(expectedUserDto).build();
            FeatureDto actualFeatureDto = featureService.getFeatureById(1L);
            assertEquals(expectedFeatureDto, actualFeatureDto);
        }

        @Test
        public void cant_get_feature_by_id() {
            List<Task> expectedTasks = new ArrayList<>();
            Task expectedTask = Task.builder().id(1L).taskRole(TaskRoles.OPEN).name("test").description("test").build();
            expectedTasks.add(expectedTask);
            UserDto expectedUserDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().id(1L).description("TEST")
                    .featureRoles(FeatureRoles.OPEN).tasks(expectedTasks).user(expectedUserDto).build();
            FeatureDto actualFeatureDto = featureService.getFeatureById(1L);
            assertNotEquals(expectedFeatureDto, actualFeatureDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("tryToCloseFeature() is working")
    class TryToCloseFeature {
        @Test
        public void try_to_close_feature() {
            List<Task> expectedTasks = new ArrayList<>();
            Task expectedTask = Task.builder().id(1L).taskRole(TaskRoles.OPEN).name("test").description("test").build();
            expectedTasks.add(expectedTask);
            UserDto expectedUserDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().id(1L).name("TEST").description("TEST")
                    .featureRoles(FeatureRoles.OPEN).tasks(expectedTasks).user(expectedUserDto).build();
            FeatureDto actualFeatureDto = featureService.tryToCloseFeature(1L);
            assertEquals(expectedFeatureDto, actualFeatureDto);
        }

        @Test
        public void cant_try_to_close_feature() {
            List<Task> expectedTasks = new ArrayList<>();
            Task expectedTask = Task.builder().id(1L).taskRole(TaskRoles.OPEN).name("test").description("test").build();
            expectedTasks.add(expectedTask);
            UserDto expectedUserDto = UserDto.builder().id(2L).login("developer@gmail.com").name("Azat").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().id(1L).description("TEST")
                    .featureRoles(FeatureRoles.OPEN).tasks(expectedTasks).user(expectedUserDto).build();
            FeatureDto actualFeatureDto = featureService.tryToCloseFeature(1L);
            assertNotEquals(expectedFeatureDto, actualFeatureDto);
        }
    }
}
