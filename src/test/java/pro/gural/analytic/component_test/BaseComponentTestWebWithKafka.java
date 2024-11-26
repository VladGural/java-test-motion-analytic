package pro.gural.analytic.component_test;


import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseComponentTestWebWithKafka extends BaseComponentTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseComponentTestWebWithKafka.class);

    @Container
    public static final KafkaContainer kafkaContainer =
            new KafkaContainer("latest")
                    .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true")
                    .withEnv("KAFKA_ADVERTISED_LISTENERS", "PLAINTEXT://localhost:3333")
                    .withKraft();

    public static class KafkaInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
            if (!kafkaContainer.isRunning()) {
                kafkaContainer.start();
                logger.info(">>> KAFKA CONTAINER STARTED. BootstrapServers: = {}", kafkaContainer.getBootstrapServers());
            }
            TestPropertyValues.of(
                    "spring.kafka.bootstrap-servers=" + kafkaContainer.getBootstrapServers()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}