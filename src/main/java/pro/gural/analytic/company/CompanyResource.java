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
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/{companyId}/current-names")
    CompanyCurrentName getCompanyCurrentName(@PathVariable String companyId) {
        logger.info("User try to get company current name: {}", companyId);
        CompanyCurrentName companyCurrentName = service.getCompanyCurrentName(companyId);
        logger.info("Company current name was successfully received: {}", companyCurrentName);
        return companyCurrentName;
    }

    @Operation(summary = "Get company current name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved company current name"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/{companyId}/names")
    CompanyNames getCompanyNames(@PathVariable String companyId) {
        logger.info("User try to get company names: {}", companyId);
        CompanyNames companyNames = service.getCompanyNames(companyId);
        logger.info("Company names was successfully received: {}", companyNames);
        return companyNames;
    }



}
