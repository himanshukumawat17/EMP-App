package emp_app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "employees")
public class EMPEntity {
	@Id
	private String id;

	private String empId;
	private String userName;
	private String password;
	private String confirmPassword;
	private String employeeType;

	private String companyName;
	private String companyCode;
}
