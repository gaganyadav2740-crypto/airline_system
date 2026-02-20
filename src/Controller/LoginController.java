package Controller;

import DTO.AdminLoginRequest;
import DTO.UserLoginRequest;
import Service.LoginService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user")
    public String userLogin(@RequestBody UserLoginRequest request) {
        return loginService.userLogin(request.getEmail(), request.getPassword());
    }

    @PostMapping("/admin")
    public String adminLogin(@RequestBody AdminLoginRequest request) {
        return loginService.adminLogin(request.getUsername(), request.getPassword());
    }
}

