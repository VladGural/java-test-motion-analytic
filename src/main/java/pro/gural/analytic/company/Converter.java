package pro.gural.analytic.company;

import org.springframework.web.servlet.tags.form.InputTag;
import pro.gural.common.domain.Company;
import pro.gural.common.domain.KafkaActionType;
import util.Util;

import java.time.Instant;
import java.util.*;

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

    public static AddressCategoryStat toAddressCategoryStat(List<String> currentCategories) {
        Map<String, Integer> addressCategoryStatMap = new HashMap<>();
        if (currentCategories == null || currentCategories.isEmpty()) {
            return new AddressCategoryStat()
                    .setAddressCategoryStat(addressCategoryStatMap);
        }
        currentCategories.stream()
                .map(Util::fromStringArrayJson)
                .flatMap(Collection::stream)
                .forEach(c -> {
                    Integer currentCount = addressCategoryStatMap.get(c);
                    if (currentCount == null) {
                        addressCategoryStatMap.put(c, 1);
                    } else {
                        addressCategoryStatMap.put(c, ++currentCount);
                    }
                });
        return new AddressCategoryStat()
                .setAddressCategoryStat(addressCategoryStatMap);
    }
}
