package repository;

import entity.Weekend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekendRepository extends JpaRepository<Weekend, Long> {
}
