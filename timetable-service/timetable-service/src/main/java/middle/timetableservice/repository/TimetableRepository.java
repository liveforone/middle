package middle.timetableservice.repository;

import middle.timetableservice.domain.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, Long>, TimetableCustomRepository {
}
