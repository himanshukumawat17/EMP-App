package emp_app.respository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import emp_app.entity.EMPEntity;

public interface EMPRepository extends MongoRepository<EMPEntity, String> {
	Optional<EMPEntity> findByUserName(String userName);

	long countByCompanyCode(String companyCode);
}
