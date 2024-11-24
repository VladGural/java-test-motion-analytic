package pro.gural.analytic;

import org.apache.kafka.common.protocol.types.Field;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.UnknownUnits;
import pro.gural.analytic.component_test.BaseComponentTestWebWithPostgres;
import pro.gural.analytic.produser.KafkaProduser;
import pro.gural.common.domain.*;

import java.util.List;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Vladyslav Gural
 * @version 2024-07-29
 */
@DirtiesContext
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = {"component-tapplication-component-test.ymlest"})
@SpringBootTest(
    classes = {
            AnalyticApplication.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(
    initializers = {
            BaseComponentTestWebWithPostgres.PostgresInitializer.class
    }
)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:3333", "port=3333"})
public class CompanyComponentIT extends BaseComponentTestWebWithPostgres {
    private static final Logger logger = LoggerFactory.getLogger(CompanyComponentIT.class);

    @BeforeAll
    void init() throws Exception {
    }

    @Test
    void test() throws Exception {

        // create Alis company
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

        Company alis = new Company()
                .setId(alisId)
                .setName("Alis")
                .setStatus(CompanyStatusType.ACTIVE)
                .setContactInformation("phone: +380503332211, mail: info@alis.pro")
                .setIndustry("food")
                .setCompanyAddress(List.of(companyAddress01, companyAddress02));

        KafkaProduser.sendCompanyEvent(alis, KafkaActionType.CREATE);

        Awaitility.await().atMost(10, SECONDS).pollInterval(500, MILLISECONDS)
                .until(() -> false);

        logger.info("Component test finished");
    }
}
