package pro.gural.analytic.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gural.common.domain.Company;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
@Tag(name = "company analytics")
@RestController
@RequestMapping("/v1/companies")
class CompanyResource {
    private static final Logger logger = LoggerFactory.getLogger(CompanyResource.class);

    private final CompanyService service;

    CompanyResource(CompanyService service) {
        this.service = service;
    }


    @Operation(summary = "Get company current name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved company current name"),
    })
    @GetMapping("/{companyId}/current-names")
    CompanyCurrentName getCompanyCurrentName(@PathVariable String companyId) {
        logger.info("User try to get company with id: {} current name", companyId);
        CompanyCurrentName companyCurrentName = service.getCompanyCurrentName(companyId);
        logger.info("Company current name was successfully received: {}", companyCurrentName);
        return companyCurrentName;
    }

    @Operation(summary = "Get company names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved company names"),
    })
    @GetMapping("/{companyId}/names")
    CompanyNames getCompanyNames(@PathVariable String companyId) {
        logger.info("User try to get company with id: {} names", companyId);
        CompanyNames companyNames = service.getCompanyNames(companyId);
        logger.info("Company names was successfully received: {}", companyNames);
        return companyNames;
    }

    @Operation(summary = "Get company address category stat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved address category stat"),
    })
    @GetMapping("/{companyId}/address-category-stats")
    AddressCategoryStat getAddressCategoryStat(@PathVariable String companyId) {
        logger.info("User try to get company with id: {} address category stat", companyId);
        AddressCategoryStat addressCategoryStat = service.getAddressCategoryStat(companyId);
        logger.info("Company address category stat was successfully received: {}", addressCategoryStat);
        return addressCategoryStat;
    }



}
