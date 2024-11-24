package pro.gural.analytic.domain;

import pro.gural.common.domain.CompanyKafkaMessage;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
public interface CompanyServiceAware {

    void saveCompanyEvent(CompanyKafkaMessage companyKafkaMessage);
}
