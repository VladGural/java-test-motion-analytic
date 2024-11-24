package pro.gural.analytic.company;

import jakarta.validation.constraints.Past;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
interface CompanyRepository extends JpaRepository<CompanyEntity, String> {

    @Query(value = """
        SELECT name
        FROM company
        WHERE id = :companyId
        ORDER BY event_time DESC
        LIMIT 1
    """, nativeQuery = true)
    String getCompanyCurrentName(@Param("companyId") String companyId);
}
