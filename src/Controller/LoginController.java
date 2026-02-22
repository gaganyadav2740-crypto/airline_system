package Controller;

import DTO.AdminLoginRequest;
import DTO.UserLoginRequest;
import DTO.LoginResponse;
import Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user")
    public ResponseEntity<LoginResponse> userLogin(@Valid @RequestBody UserLoginRequest request, HttpServletRequest httpRequest) {
        LoginResponse response = loginService.userLogin(request.getEmail(), request.getPassword(), httpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<LoginResponse> adminLogin(@Valid @RequestBody AdminLoginRequest request, HttpServletRequest httpRequest) {
        LoginResponse response = loginService.adminLogin(request.getUsername(), request.getPassword(), httpRequest);
        return ResponseEntity.ok(response);
    }
}

