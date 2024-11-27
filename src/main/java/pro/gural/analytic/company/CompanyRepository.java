package pro.gural.analytic.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    @Query(value = """
        SELECT name
        FROM company
        WHERE id = :companyId
        ORDER BY event_time DESC
    """, nativeQuery = true)
    List<String> getCompanyNames(@Param("companyId") String companyId);

    @Query(value = """
        SELECT category
        FROM company_address ca
        INNER JOIN (SELECT MAX(event_time) as last_event_time
                    FROM company
                    WHERE id = :companyId
                    GROUP BY id) last_event ON last_event.last_event_time = ca.event_time
        WHERE ca.company_id = :companyId
    """, nativeQuery = true)
    List<String> getCurrentCategories(@Param("companyId") String companyId);
}
