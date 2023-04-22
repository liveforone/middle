package middle.recommendservice.repository;

import middle.recommendservice.domain.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long>, RecommendCustomRepository {
}
