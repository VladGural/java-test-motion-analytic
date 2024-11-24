package pro.gural.analytic.company;

import pro.gural.common.domain.Company;
import pro.gural.common.domain.KafkaActionType;

import java.time.Instant;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
class Converter {
    public static CompanyEntity toCompanyEntity(Company company, KafkaActionType actionType, Instant eventTime) {
        return new CompanyEntity()
                .setId(company.getId())
                .setName(company.getName())
                .setStatus(company.getStatus())
                .setContactInformation(company.getContactInformation())
                .setIndustry(company.getIndustry())
                .setEventAction(actionType)
                .setEventTime(eventTime);
    }
}
