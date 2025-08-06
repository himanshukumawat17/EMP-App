package emp_app.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWrapper<T> {
    private String message;
    private boolean success;
    private T data;
}
