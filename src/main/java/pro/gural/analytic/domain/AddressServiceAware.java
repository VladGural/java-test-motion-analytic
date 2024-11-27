package pro.gural.analytic.domain;

import org.apache.kafka.common.protocol.types.Field;
import pro.gural.common.domain.CompanyAddress;

import java.time.Instant;
import java.util.List;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
public interface AddressServiceAware {

    void saveAddressEvents(List<CompanyAddress> companyAddressList, Instant eventTime, String eventId);
}
