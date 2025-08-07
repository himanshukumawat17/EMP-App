package emp_app.respository;

import emp_app.entity.FieldVisit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FieldVisitRepository extends MongoRepository<FieldVisit, String> {
}
