package pro.gural.analytic.address;

import pro.gural.common.domain.CompanyAddress;
import util.Util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
class Converter {
    public static List<AddressEntity> toAddressEntityList(List<CompanyAddress> companyAddressList, Instant eventTime) {
        if (companyAddressList == null) {
            return new ArrayList<>();
        }
        return companyAddressList.stream()
                .map(a -> toAddressEntity(a, eventTime))
                .collect(Collectors.toList());
    }

    public static AddressEntity toAddressEntity(CompanyAddress companyAddress, Instant eventTime) {
        if (companyAddress == null) {
            return null;
        }
        return new AddressEntity()
                .setId(companyAddress.getId())
                .setCompanyId(companyAddress.getCompanyId())
                .setCountry(companyAddress.getCountry())
                .setCity(companyAddress.getCity())
                .setStreet(companyAddress.getStreet())
                .setZip(companyAddress.getZip())
                .setAddressCategory(Util.toJson(companyAddress.getAddressCategory()))
                .setEventTime(eventTime);
    }
}
