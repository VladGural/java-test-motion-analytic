package pro.gural.analytic.company;

import pro.gural.common.domain.Company;
import pro.gural.common.domain.KafkaActionType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
class Converter {
    public static CompanyEntity toCompanyEntity(Company company, KafkaActionType actionType, Instant eventTime) {
        return new CompanyEntity()
                .setEventId(UUID.randomUUID().toString())
                .setId(company.getId())
                .setName(company.getName())
                .setStatus(company.getStatus())
                .setContactInformation(company.getContactInformation())
                .setIndustry(company.getIndustry())
                .setEventAction(actionType)
                .setEventTime(eventTime);
    }

    public static CompanyNames toCompanyNames(List<String> companyNames) {
        if (companyNames == null || companyNames.isEmpty()) {
            return new CompanyNames()
                    .setCurrentName(null)
                    .setPreviousNames(new ArrayList<>());
        }
        String currentName = companyNames.getFirst();
        companyNames.remove(currentName);
        return new CompanyNames()
                .setCurrentName(currentName)
                .setPreviousNames(companyNames);
    }
}
