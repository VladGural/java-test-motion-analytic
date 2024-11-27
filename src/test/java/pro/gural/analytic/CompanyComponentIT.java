package pro.gural.analytic;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pro.gural.analytic.component_test.BaseComponentTestWebWithKafka;
import pro.gural.analytic.component_test.BaseComponentTestWebWithPostgres;
import pro.gural.analytic.producer.KafkaProducer;
import pro.gural.common.domain.*;

import java.util.List;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static pro.gural.analytic.company.CompanyClient.*;
import static pro.gural.common.domain.AddressCategoryType.*;
import static pro.gural.common.domain.CompanyStatusType.ACTIVE;
import static pro.gural.common.domain.CompanyStatusType.BLOCKED;

/**
 * @author Vladyslav Gural
 * @version 2024-07-29
 */
@DirtiesContext
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = {"component-test"})
@SpringBootTest(
    classes = {
            AnalyticApplication.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(
    initializers = {
            BaseComponentTestWebWithPostgres.PostgresInitializer.class,
            BaseComponentTestWebWithKafka.KafkaInitializer.class
    }
)
public class CompanyComponentIT extends BaseComponentTestWebWithPostgres {
    private static final Logger logger = LoggerFactory.getLogger(CompanyComponentIT.class);

    @BeforeAll
    void init() throws Exception {
    }

    @Test
    void test() throws Exception {

        //
        // create company1
        //
        String alisId = UUID.randomUUID().toString();
        CompanyAddress companyAddress01 = new CompanyAddress()
                .setId(UUID.randomUUID().toString())
                .setCompanyId(alisId)
                .setCountry("Ukraine")
                .setCity("Kyiv")
                .setStreet("Vasilya Tutunnika")
                .setZip("03150")
                .setAddressCategory(List.of(AddressCategoryType.HEADQUARTER, AddressCategoryType.DISTRIBUTION_CENTER));

        CompanyAddress companyAddress02 = new CompanyAddress()
                .setId(UUID.randomUUID().toString())
                .setCompanyId(alisId)
                .setCountry("Ukraine")
                .setCity("Lviv")
                .setStreet("Velika Gora")
                .setZip("02011")
                .setAddressCategory(List.of(AddressCategoryType.HEADQUARTER));

        Company company1 = new Company()
                .setId(alisId)
                .setName("Alis")
                .setStatus(CompanyStatusType.ACTIVE)
                .setContactInformation("phone: +380503332211, mail: info@alis.pro")
                .setIndustry("food")
                .setCompanyAddress(List.of(companyAddress01, companyAddress02));

        // send company1 Kafka event
        CompanyKafkaMessage company1KafkaMessage = KafkaProducer.sendCompanyEvent(company1, KafkaActionType.CREATE);

        // wait receiving company1 Kafka event
        Awaitility.await().atMost(10, SECONDS).pollInterval(500, MILLISECONDS)
                .until(() -> isCurrentNameCorrect(getCompanyCurrentName(this, alisId), "Alis"));

        // check getCurrentName endpoint
        checkCurrentName(getCompanyCurrentName(this, alisId), "Alis");

        // check getNames endpoint
        checkNames(getCompanyNames(this, alisId), "Alis", List.of());

        //
        // first update company1
        // update company name, contactInformation and company addresses

        // create 2 new company1 address update company1 address in Kyiv and delete company1 Address in Lviv
        companyAddress01 = new CompanyAddress()
                .setId(UUID.randomUUID().toString())
                .setCompanyId(alisId)
                .setCountry("Ukraine")
                .setCity("Odesa")
                .setStreet("Chernomorska")
                .setZip("04223")
                .setAddressCategory(List.of(WAREHOUSE));

        companyAddress02 = new CompanyAddress()
                .setId(UUID.randomUUID().toString())
                .setCompanyId(alisId)
                .setCountry("Ukraine")
                .setCity("Dnipro")
                .setStreet("Dniprovska")
                .setZip("05332")
                .setAddressCategory(List.of(BRANCH_OFFICE, WAREHOUSE));

        CompanyAddress companyAddress03 = getAddressByCity(company1KafkaMessage.getCompany().getCompanyAddress(), "Kyiv");
        companyAddress03
                .setStreet("Petra Sagaydachnogo")
                .setZip("03133")
                .setAddressCategory(List.of(HEADQUARTER, DISTRIBUTION_CENTER, WAREHOUSE));

        company1 = company1KafkaMessage.getCompany();
        company1
                .setName("Alis-1")
                .setContactInformation("phone: +380503332211, +380508885533, mail: info@alis.pro")
                .setCompanyAddress(List.of(companyAddress01, companyAddress02, companyAddress03));

        // send company1 Kafka event
        company1KafkaMessage = KafkaProducer.sendCompanyEvent(company1, KafkaActionType.UPDATE);

        // wait receiving company1 Kafka event
        Awaitility.await().atMost(10, SECONDS).pollInterval(500, MILLISECONDS)
                .until(() -> isCurrentNameCorrect(getCompanyCurrentName(this, alisId), "Alis-1"));

        // check getCurrentName endpoint
        checkCurrentName(getCompanyCurrentName(this, alisId), "Alis-1");

        // check getNames endpoint
        checkNames(getCompanyNames(this, alisId), "Alis-1", List.of("Alis"));

        //
        // second update company1
        // update company name and company status
        company1 = company1KafkaMessage.getCompany();
        company1
                .setName("Alis-2")
                .setStatus(BLOCKED);

        // send company1 Kafka event
        company1KafkaMessage = KafkaProducer.sendCompanyEvent(company1, KafkaActionType.UPDATE);

        // wait receiving company1 Kafka event
        Awaitility.await().atMost(10, SECONDS).pollInterval(500, MILLISECONDS)
                .until(() -> isCurrentNameCorrect(getCompanyCurrentName(this, alisId), "Alis-2"));

        // check getCurrentName endpoint
        checkCurrentName(getCompanyCurrentName(this, alisId), "Alis-2");

        // check getNames endpoint
        checkNames(getCompanyNames(this, alisId), "Alis-2", List.of("Alis-1", "Alis"));

        logger.info("Component test finished");
    }
}
