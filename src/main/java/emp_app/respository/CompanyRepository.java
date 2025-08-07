package emp_app.respository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import emp_app.entity.CompanyEntity;

public interface CompanyRepository extends MongoRepository<CompanyEntity, String> {
	Optional<CompanyEntity> findByCompanyName(String companyName);
}
