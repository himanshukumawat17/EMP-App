package emp_app.respository;


import emp_app.entity.EMPEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EMPRepository extends MongoRepository<EMPEntity, String> {
    Optional<EMPEntity> findByUserName(String userName);
}
