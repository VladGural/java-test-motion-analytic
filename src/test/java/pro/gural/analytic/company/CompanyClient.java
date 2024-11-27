package pro.gural.analytic.company;

import org.apache.kafka.common.protocol.types.Field;
import pro.gural.analytic.component_test.BaseComponentTest;
import pro.gural.common.domain.CompanyAddress;
import util.Util;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
public class CompanyClient {
    public static CompanyCurrentName getCompanyCurrentName(BaseComponentTest restCall, String companyId) throws Exception {
        String url = "/v1/companies/" + companyId +"/current-names";
        String get = restCall.get(url);
        return Util.fromJson(get, CompanyCurrentName.class);
    }

    public static CompanyNames getCompanyNames(BaseComponentTest restCall, String companyId) throws Exception {
        String url = "/v1/companies/" + companyId +"/names";
        String get = restCall.get(url);
        return Util.fromJson(get, CompanyNames.class);
    }

    public static AddressCategoryStat getCompanyAddressCategoryStat(BaseComponentTest restCall, String companyId) throws Exception {
        String url = "/v1/companies/" + companyId +"/address-category-stats";
        String get = restCall.get(url);
        return Util.fromJson(get, AddressCategoryStat.class);
    }

    public static CompanyCurrentStatus getCompanyCurrentStatus(BaseComponentTest restCall, String companyId) throws Exception {
        String url = "/v1/companies/" + companyId +"/current-statuses";
        String get = restCall.get(url);
        return Util.fromJson(get, CompanyCurrentStatus.class);
    }

    public static boolean isCurrentNameCorrect(CompanyCurrentName companyCurrentName, String checkCurrentName) {
        return checkCurrentName.equals(companyCurrentName.getCurrentName());
    }

    public static void checkCurrentName(CompanyCurrentName companyCurrentName, String checkCurrentName) {
        assertThat(checkCurrentName.equals(companyCurrentName.getCurrentName()), is(true));
    }

    public static void checkNames(CompanyNames companyNames, String checkCurrentName, List<String> checkPreviousNames) {
        assertThat(checkCurrentName.equals(companyNames.getCurrentName()), is(true));
        assertThat(checkPreviousNames.equals(companyNames.getPreviousNames()), is(true));
    }

    public static void checkCategoryStat(AddressCategoryStat addressCategoryStat, Map<String, Integer> addressCategoryStatMap) {
        assertThat(addressCategoryStat.getAddressCategoryStat().size(), is(addressCategoryStatMap.size()));
        for (String category : addressCategoryStatMap.keySet()) {
            assertThat(addressCategoryStat.getAddressCategoryStat().get(category)
                    .equals(addressCategoryStatMap.get(category)), is(true));
        }
    }

    public static CompanyAddress getAddressByCity(List<CompanyAddress> addresses, String city) {
        return addresses.stream()
                .filter(a -> city.equals(a.getCity()))
                .findFirst().orElse(null);
    }

    public static void checkCurrentStatus(CompanyCurrentStatus companyCurrentName, String checkCurrentStatus) {
        assertThat(checkCurrentStatus.equals(companyCurrentName.getCurrentStatus()), is(true));
    }


}
