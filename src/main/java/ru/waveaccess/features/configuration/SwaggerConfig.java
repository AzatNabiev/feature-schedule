package ru.waveaccess.features.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String FEATURE_TAG = "Feature endpoint";
    public static final String TASK_TAG = "Task endpoint";
    public static final String USER_TAG = "User endpoint";
    public static final String AUTH_TAG = "Authentication endpoint";
    public static final String REG_TAG = "Registration endpoint";

    @Bean
    public Docket featureApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag(FEATURE_TAG, "Working with features"),
                        new Tag(TASK_TAG, "Working with tasks"),
                        new Tag(USER_TAG, "Giving role to User By Manager"),
                        new Tag(AUTH_TAG, "Authentication"),
                        new Tag(REG_TAG, "Registration"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Feature API",
                "Task tracking system",
                "Beta",
                "Terms of service",
                new Contact("Azat Nabiev", "www.example.com", "myeaddress@company.com"),
                "License of API", "API license URL", Collections.emptyList());

    }
}
