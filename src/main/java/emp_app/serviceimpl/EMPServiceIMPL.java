package emp_app.serviceimpl;

import emp_app.entity.EMPEntity;
import emp_app.respository.EMPRepository;
import emp_app.service.EMPService;
import emp_app.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EMPServiceIMPL implements EMPService {

	@Autowired
	private EMPRepository repository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<?> signup(EMPEntity user) {
		Optional<EMPEntity> existingUser = repository.findByUserName(user.getUserName());

		if (existingUser.isPresent()) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("message", "User already exists"));
		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Passwords do not match"));
		}

		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);

		user.setConfirmPassword(null);

		repository.save(user);

		return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
	}

	@Override
	public ResponseEntity<?> login(String username, String password) {
		System.out.println("Login attempt for username: [" + username + "]");

		Optional<EMPEntity> userOpt = repository.findByUserName(username);

		if (userOpt.isEmpty()) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("message", "User not found"));
		}

		EMPEntity user = userOpt.get();

		if (!passwordEncoder.matches(password, user.getPassword())) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Wrong password"));
		}

		String token = jwtUtil.generateToken(username);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Login successful");
		response.put("token", token);
		response.put("userName", user.getUserName());
		response.put("userId", user.getId());

		return ResponseEntity.ok(response);
	}
}
