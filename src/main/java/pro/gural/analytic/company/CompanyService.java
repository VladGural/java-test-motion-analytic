package pro.gural.analytic.company;

import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.gural.analytic.domain.AddressServiceAware;
import pro.gural.analytic.domain.CompanyServiceAware;
import pro.gural.common.domain.CompanyKafkaMessage;

import static pro.gural.analytic.company.Converter.toCompanyEntity;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
@Service
class CompanyService implements CompanyServiceAware {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository repo;
    private final AddressServiceAware addressService;

    CompanyService(CompanyRepository repo,
                   AddressServiceAware addressService) {
        this.repo = repo;
        this.addressService = addressService;
    }

    @Override
    public void saveCompanyEvent(CompanyKafkaMessage companyKafkaMessage) {
        CompanyEntity companyEntity = toCompanyEntity(companyKafkaMessage.getCompany(),
                companyKafkaMessage.getAction(), companyKafkaMessage.getEventTime());
        repo.saveAndFlush(companyEntity);
        addressService.saveAddressEvents(companyKafkaMessage.getCompany().getCompanyAddress(),
                companyKafkaMessage.getEventTime());
        logger.info("Company event was saved");
    }

    public CompanyCurrentName getCompanyCurrentName(String companyId) {
        String companyCurrentName = repo.getCompanyCurrentName(companyId);
        return new CompanyCurrentName()
                .setCurrentName(companyCurrentName);
    }
}