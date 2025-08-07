package emp_app.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "leads")
public class Lead {
    @Id
    private String id;

    private String title;
    private String description;
    private String image;
    private String remark;
    private String assignTo;
    private String status = "open"; 

    private Date createdDate;
    private Date updatedDate;
}
