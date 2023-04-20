package middle.shopservice.repository;

import middle.shopservice.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopCustomRepository {
}
