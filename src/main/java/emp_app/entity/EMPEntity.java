package emp_app.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class EMPEntity {
    @Id
    private String id;

    private String empId;             // ✅ New field
    private String userName;
    private String password;
    private String confirmPassword;   // ✅ New field
}
