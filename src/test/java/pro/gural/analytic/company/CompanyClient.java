package pro.gural.analytic.company;

import org.hamcrest.core.Is;
import pro.gural.analytic.component_test.BaseComponentTest;
import util.Util;

import static org.hamcrest.MatcherAssert.assertThat;

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

    public static boolean isCurrentNameCorrect(CompanyCurrentName companyCurrentName, String checkCurrentName) {
        return checkCurrentName.equals(companyCurrentName.getCurrentName());
    }

    public static void checkCurrentName(CompanyCurrentName companyCurrentName, String checkCurrentName) {
        assertThat(checkCurrentName.equals(companyCurrentName.getCurrentName()), Is.is(true));
    }


}
