package emp_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emp_app.entity.EMPEntity;
import emp_app.service.EMPService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") 
public class AuthController {

    @Autowired
    private EMPService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody EMPEntity request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EMPEntity request) {
        return authService.login(request.getUserName(), request.getPassword());
    }
}
