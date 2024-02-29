package application.repository;

import application.model.ReportDate;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDateRepository extends MongoRepository<ReportDate, String> {
    Optional<ReportDate> findByDate(LocalDate localDate);
}
