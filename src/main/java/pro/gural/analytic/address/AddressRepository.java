package pro.gural.analytic.address;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vladyslav Gural
 * @version 2024-11-24
 */
interface AddressRepository extends JpaRepository<AddressEntity, String> {
}
