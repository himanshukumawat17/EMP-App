package emp_app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document(collection = "users")
public class EMPEntity {
    @Id
    private String id;

    private String userName;
    private String password;
}
