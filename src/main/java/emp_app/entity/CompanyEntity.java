package emp_app.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "companies")
public class CompanyEntity {
	@Id
	private String id;

	private String companyName;
	private String companyCode;

	private List<String> employeeIds = new ArrayList<>();
}
