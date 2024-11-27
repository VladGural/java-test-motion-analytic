package pro.gural.analytic.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.gural.analytic.domain.AddressServiceAware;
import pro.gural.common.domain.CompanyAddress;

import java.time.Instant;
import java.util.List;

import static pro.gural.analytic.address.Converter.toAddressEntityList;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
@Service
class AddressService implements AddressServiceAware {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository repo;

    AddressService(AddressRepository repo) {
        this.repo = repo;
    }

    @Override
    public void saveAddressEvents(List<CompanyAddress> companyAddressList, Instant eventTime, String eventId) {
        List<AddressEntity> addressEntityList = toAddressEntityList(companyAddressList, eventTime, eventId);
        repo.saveAllAndFlush(addressEntityList);
    }
}
