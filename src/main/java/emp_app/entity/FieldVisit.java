package emp_app.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "field_visits")
public class FieldVisit {
    @Id
    private String id;

    private String leadId;
    private String assignTo = "Admin";  
    private String status = "open";    

    private Date createdDate;
    private Date updatedDate;
}
