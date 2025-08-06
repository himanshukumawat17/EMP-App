package emp_app.respository;



import emp_app.entity.EMPEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EMPRepository extends MongoRepository<EMPEntity, String> {
    Optional<EMPEntity> findByUserName(String userName);
}
