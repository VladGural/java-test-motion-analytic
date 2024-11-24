package pro.gural.analytic.produser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pro.gural.common.domain.Company;
import pro.gural.common.domain.CompanyKafkaMessage;
import pro.gural.common.domain.KafkaActionType;
import util.Util;

import java.time.Instant;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
@Component
public class KafkaProduser {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProduser.class);

    private static final String COMPANY_TOPIC = "company.event.v1";

    private static KafkaTemplate<String, String> kafkaTemplate;

    public static void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        KafkaProduser.kafkaTemplate = kafkaTemplate;
    }

    public static void sendCompanyEvent(Company company, KafkaActionType action) {
        CompanyKafkaMessage companyKafkaMessage = new CompanyKafkaMessage()
                .setAction(action)
                .setCompany(company)
                .setEventTime(Instant.now());
        kafkaTemplate.send(COMPANY_TOPIC, Util.toJson(companyKafkaMessage));
    }

}
