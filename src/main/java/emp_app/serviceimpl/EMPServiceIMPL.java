package emp_app.serviceimpl;

import emp_app.entity.EMPEntity;
import emp_app.respository.EMPRespository;
import emp_app.service.EMPService;
import emp_app.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EMPServiceIMPL implements EMPService {

    @Autowired
    private EMPRespository repository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> signup(EMPEntity user) {
        Optional<EMPEntity> existingUser = repository.findByUserName(user.getUserName());
        if (existingUser.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("message", "User already exists"));
        }

        repository.save(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
    }

    @Override
    public ResponseEntity<?> login(String username, String password) {
        Optional<EMPEntity> userOpt = repository.findByUserName(username);

        if (userOpt.isPresent()) {
            EMPEntity user = userOpt.get();
            if (user.getPassword().equals(password)) {
                String token = jwtUtil.generateToken(username);
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Wrong password"));
            }
        }

        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "User not found"));
    }
}
