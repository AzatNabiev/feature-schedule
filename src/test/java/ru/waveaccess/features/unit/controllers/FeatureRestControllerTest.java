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
import ru.waveaccess.features.dto.FeatureDto;
import ru.waveaccess.features.forms.FeatureForm;
import ru.waveaccess.features.services.FeatureService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName(value = "FeatureRestController is working when")
public class FeatureRestControllerTest extends DemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeatureService featureService;

    @BeforeEach
    public void setUp() {
        List<FeatureDto> featureDtoList = new ArrayList<>();
        FeatureDto featureDto = FeatureDto.builder().id(1L).name("TEST").description("TEST").build();
        FeatureForm featureForm = FeatureForm.builder().featureName("TEST").featureDescription("TEST").build();
        featureDtoList.add(featureDto);
        when(featureService.getAllFeatures()).thenReturn(featureDtoList);
        when(featureService.getFeatureById(1L)).thenReturn(featureDto);
        when(featureService.addFeature(featureForm)).thenReturn(featureDto);
        when(featureService.tryToCloseFeature(1L)).thenReturn(FeatureDto.builder().build());
        when(featureService.addUserToFeature(featureForm)).thenReturn(FeatureDto.builder().build());
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getAllFeatures() is working")
    class getAllFeatures {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTg4NjgwMH0.QI31tgtpNksf37VaLNHA93sWZWc-oTkmHhXb7w6kIIUGv1n0z734_Z2ubuvkE6CD3rFT9GqJnuEMrfVbMKYEcg"})
        public void return_all_features(String token) throws Exception {
            mockMvc.perform(get("/api/feature/all/features")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_get_all_feature() throws Exception {
            mockMvc.perform(get("/api/feature/all/features"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getFeature() is working")
    class getFeature {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTgwMDQwMH0.rbvegQmO8M_6qlgBcFm8yyqhiWnvLtgel8za5zH_yjfvK78v-Lyi2WEsc3HvUHnhyM0e4jRYK4Jpw-B1NCUXEw"})
        public void return_feature(String token) throws Exception {
            mockMvc.perform(get("/api/feature/all/features/1")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_get_feature() throws Exception {
            mockMvc.perform(get("/api/feature/all/features/1"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("createFeature() is working")
    class createFeature {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTgwMDQwMH0.rbvegQmO8M_6qlgBcFm8yyqhiWnvLtgel8za5zH_yjfvK78v-Lyi2WEsc3HvUHnhyM0e4jRYK4Jpw-B1NCUXEw"})
        public void create_feature(String token) throws Exception {
            mockMvc.perform(post("/api/feature")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"featureName\": \"feature name\",\n" +
                                    "  \"featureDescription\": \"feature description\"\n" +
                                    "}")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_create_feature() throws Exception {
            mockMvc.perform(post("/api/feature"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("addUserToFeature() is working")
    class addUserToFeature {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTgwMDQwMH0.rbvegQmO8M_6qlgBcFm8yyqhiWnvLtgel8za5zH_yjfvK78v-Lyi2WEsc3HvUHnhyM0e4jRYK4Jpw-B1NCUXEw"})
        public void add_user_to_feature(String token) throws Exception {
            mockMvc.perform(post("/api/feature/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"featureId\": \"1\",\n" +
                                    "  \"userId\": \"2\"\n" +
                                    "}")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_add_user_to_feature() throws Exception {
            mockMvc.perform(post("/api/feature/user"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("tryToClose() is working")
    class tryToClose {
        @ParameterizedTest
        @ValueSource(strings = {"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYzNTgwMDQwMH0.rbvegQmO8M_6qlgBcFm8yyqhiWnvLtgel8za5zH_yjfvK78v-Lyi2WEsc3HvUHnhyM0e4jRYK4Jpw-B1NCUXEw"})
        public void try_to_close_feature(String token) throws Exception {
            mockMvc.perform(post("/api/feature/1")
                            .header("X-access-token", token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        public void access_denied_for_try_to_close_feature() throws Exception {
            mockMvc.perform(post("/api/feature/1"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

}
