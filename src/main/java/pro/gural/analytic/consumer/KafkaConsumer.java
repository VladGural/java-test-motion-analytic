package pro.gural.analytic.consumer;

import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import pro.gural.common.domain.CompanyKafkaMessage;
import util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
@Service
class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private static final String COMPANY_TOPIC = "company.event.v1";

    @KafkaListener(topics = COMPANY_TOPIC)
    void receiveEmail(@Payload String message) {
        CompanyKafkaMessage companyKafkaMessage = Util.fromJson(message, CompanyKafkaMessage.class);
        if (companyKafkaMessage == null) {
            logger.error("Cannot parse message: {}", message);
            return;
        }
        logger.info("Receive kafka message: {}", companyKafkaMessage);
        // TODO prossesing

    }
}
