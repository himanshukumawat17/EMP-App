package emp_app.controller;

import emp_app.entity.EMPEntity;
import emp_app.service.EMPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class EMPController {

    @Autowired
    private EMPService service;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody EMPEntity user) {
        return service.signup(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EMPEntity request) {
        return service.login(request.getUserName(), request.getPassword());
    }
}
