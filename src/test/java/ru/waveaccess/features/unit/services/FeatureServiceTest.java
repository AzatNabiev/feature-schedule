package ru.waveaccess.features.unit.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.waveaccess.features.DemoApplicationTest;
import ru.waveaccess.features.dto.FeatureDto;
import ru.waveaccess.features.enums.FeatureRoles;
import ru.waveaccess.features.forms.FeatureForm;
import ru.waveaccess.features.models.Feature;
import ru.waveaccess.features.models.Task;
import ru.waveaccess.features.models.User;
import ru.waveaccess.features.repositories.FeatureJpaRepository;

import ru.waveaccess.features.repositories.UserJpaRepository;
import ru.waveaccess.features.services.FeatureService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName(value = "FeatureServiceImpl is working")
class FeatureServiceTest extends DemoApplicationTest {

    @Autowired
    private FeatureService featureService;

    @MockBean
    private FeatureJpaRepository featureJpaRepository;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @BeforeEach
    public void setUp() {
        List<Feature> featureList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        Feature feature = Feature.builder().name("TEST").tasks(taskList).build();
        featureList.add(feature);
        when(featureJpaRepository.save(feature)).thenReturn(feature);
        when(featureJpaRepository.findAll()).thenReturn(featureList);

        User user = User.builder().id(1L).build();

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(featureJpaRepository.findById(1L)).thenReturn(Optional.of(feature));

    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("addFeature() is working")
    class addingFeature {
        @Test
        public void add_feature() {
            // data init
            FeatureForm featureForm = FeatureForm.builder().featureName("TEST").featureDescription("TEST").build();
            FeatureDto expectedDto = FeatureDto.builder().name("TEST").featureRoles(FeatureRoles.OPEN).description("TEST").build();

            // executing
            FeatureDto actualDto = featureService.addFeature(featureForm);

            // asserting result
            assertEquals(expectedDto, actualDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getAllFeatures() is working")
    class getAllFeatures {
        @Test
        public void get_all_features() {
            // data init
            List<FeatureDto> expectedFeatureDtoList = new ArrayList<>();
            expectedFeatureDtoList.add(FeatureDto.builder().tasks(new ArrayList<>()).name("TEST").build());

            // executing
            List<FeatureDto> actualFeatureDtoList = featureService.getAllFeatures();

            // asserting result
            assertEquals(expectedFeatureDtoList, actualFeatureDtoList);

        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("addUserToFeature() is working")
    class addUserToFeature {

        @Test
        public void add_user_to_feature() {
            // data init
            FeatureForm featureForm = FeatureForm.builder().featureId("1L").build();
            FeatureDto expectedFeatureDto = FeatureDto.builder().featureRoles(FeatureRoles.OPEN).build();

            // executing
            FeatureDto actualFeatureDto = featureService.addFeature(featureForm);

            // asserting result
            assertEquals(expectedFeatureDto, actualFeatureDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getFeatureById() is working")
    class getFeatureById {

        @Test
        public void get_feature_by_id() {
            // data init
            FeatureDto expectedFeatureDto = FeatureDto.builder().tasks(new ArrayList<>()).name("TEST").build();

            // executing
            FeatureDto actualFeatureDto = featureService.getFeatureById(1L);

            // asserting result
            assertEquals(expectedFeatureDto, actualFeatureDto);
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("tryToCloseFeature() is working")
    class tryToCloseFeature {
        @Test
        public void try_to_close_feature() {
            // data init
            FeatureDto expectedFeatureDto = FeatureDto.builder().tasks(new ArrayList<>()).featureRoles(FeatureRoles.COMPLETED)
                    .name("TEST").build();

            // executing
            FeatureDto actualFeatureDto = featureService.tryToCloseFeature(1L);

            // asserting result
            assertEquals(expectedFeatureDto, actualFeatureDto);
        }
    }
}