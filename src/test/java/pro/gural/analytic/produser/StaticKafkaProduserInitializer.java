package pro.gural.analytic.produser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import jakarta.annotation.PostConstruct;

/**
 * @author Vladyslav Gural
 * @version 2023-12-26
 */
@Configuration
public class StaticKafkaProduserInitializer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    public void init() {
        KafkaProduser.setKafkaTemplate(kafkaTemplate);
    }
}
