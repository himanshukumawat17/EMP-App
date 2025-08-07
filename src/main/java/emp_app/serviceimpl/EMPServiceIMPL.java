package emp_app.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import emp_app.entity.CompanyEntity;
import emp_app.entity.EMPEntity;
import emp_app.respository.CompanyRepository;
import emp_app.respository.EMPRepository;
import emp_app.service.EMPService;
import emp_app.utils.JwtUtil;

@Service
public class EMPServiceIMPL implements EMPService {

	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder passwordEncoder;
	private final EMPRepository empRepository;
	private final CompanyRepository companyRepository;

	public EMPServiceIMPL(JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder, EMPRepository empRepository,
			CompanyRepository companyRepository) {
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
		this.empRepository = empRepository;
		this.companyRepository = companyRepository;
	}

	@Override
	public ResponseEntity<?> signup(EMPEntity user) {
		Optional<EMPEntity> existingUser = empRepository.findByUserName(user.getUserName());
		if (existingUser.isPresent()) {
			return ResponseEntity.badRequest().body(Map.of("message", "User already exists"));
		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			return ResponseEntity.badRequest().body(Map.of("message", "Passwords do not match"));
		}

		String companyCode;
		CompanyEntity company;

		Optional<CompanyEntity> existingCompanyOpt = companyRepository.findByCompanyName(user.getCompanyName());
		if (existingCompanyOpt.isPresent()) {
			company = existingCompanyOpt.get();
			companyCode = company.getCompanyCode();
		} else {

			company = new CompanyEntity();
			company.setCompanyName(user.getCompanyName());
			companyCode = user.getCompanyName().substring(0, 3).toUpperCase();
			company.setCompanyCode(companyCode);
			company.setEmployeeIds(new ArrayList<>());
			company = companyRepository.save(company);
		}

		long count = empRepository.countByCompanyCode(companyCode) + 1;
		String empId = companyCode + String.format("%03d", count);

		user.setCompanyCode(companyCode);
		user.setEmpId(empId);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setConfirmPassword(null);

		EMPEntity savedUser = empRepository.save(user);

		List<String> employeeIds = company.getEmployeeIds();
		if (employeeIds == null) {
			employeeIds = new ArrayList<>();
		}
		employeeIds.add(savedUser.getId());
		company.setEmployeeIds(employeeIds);
		companyRepository.save(company);

		return ResponseEntity.ok(Map.of("message", "User registered successfully", "empId", empId, "userId",
				savedUser.getId(), "companyCode", companyCode));
	}

	@Override
	public ResponseEntity<?> login(String userName, String password) {
		Optional<EMPEntity> userOpt = empRepository.findByUserName(userName);
		if (userOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
		}

		EMPEntity user = userOpt.get();

		if (!passwordEncoder.matches(password, user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
		}

		String token = jwtUtil.generateToken(user.getUserName());

		return ResponseEntity.ok(Map.of("message", "Login successful", "token", token, "userId", user.getId(), "empId",
				user.getEmpId(), "companyCode", user.getCompanyCode(), "companyName", user.getCompanyName()));
	}
}
