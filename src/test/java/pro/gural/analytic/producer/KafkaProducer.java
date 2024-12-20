package pro.gural.analytic.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import pro.gural.common.domain.Company;
import pro.gural.common.domain.CompanyKafkaMessage;
import pro.gural.common.domain.KafkaActionType;
import pro.gural.common.domain.KafkaTopics;
import util.Util;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
@Component
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private static final String DEFAULT_TEMPLATE = "default";


    private static Map<String, KafkaTemplate<String, String>> kafkaTemplateMap = new HashMap<>();

    public KafkaProducer(KafkaTemplate<String, String> defaultTemplate) {
        kafkaTemplateMap.put(DEFAULT_TEMPLATE, defaultTemplate);
    }

    public static CompanyKafkaMessage sendCompanyEvent(Company company, KafkaActionType action) {
        CompanyKafkaMessage companyKafkaMessage = new CompanyKafkaMessage()
                .setAction(action)
                .setCompany(company)
                .setEventTime(Instant.now());
        CompletableFuture<SendResult<String, String>> future =
                getTemplate().send(KafkaTopics.COMPANY_TOPIC, company.getId(), Util.toJson(companyKafkaMessage));
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Kafka company message: {} was sent.",companyKafkaMessage);
            } else {
                logger.info("Unable to send kafka company message {}", companyKafkaMessage);
            }
        });
        return companyKafkaMessage;
    }

    private static KafkaTemplate<String, String> getTemplate() {
        return kafkaTemplateMap.get(DEFAULT_TEMPLATE);
    }

}
