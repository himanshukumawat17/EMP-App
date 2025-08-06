package emp_app.service;

import org.springframework.http.ResponseEntity;

import emp_app.entity.EMPEntity;

public interface EMPService {
  

	ResponseEntity<?> signup(EMPEntity user);

	ResponseEntity<?> login(String userName, String password);
}
