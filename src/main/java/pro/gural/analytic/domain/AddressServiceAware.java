package pro.gural.analytic.domain;

import pro.gural.common.domain.CompanyAddress;

import java.time.Instant;
import java.util.List;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
public interface AddressServiceAware {

    void saveAddressEvents(List<CompanyAddress> companyAddressList, Instant eventTime);
}
