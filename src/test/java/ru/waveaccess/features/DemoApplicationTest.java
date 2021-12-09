package ru.waveaccess.features;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.waveaccess.features.integration.initializer.Postgres;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = {
        Postgres.Initializer.class
})
@Transactional
public class DemoApplicationTest {
    @BeforeAll
    static void init() {
        Postgres.container.start();
    }
}
