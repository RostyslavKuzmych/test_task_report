package application.repository;

import application.model.ReportAsin;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportAsinRepository extends MongoRepository<ReportAsin, String> {
    Optional<ReportAsin> findByParentAsin(String parentAsin);
}
