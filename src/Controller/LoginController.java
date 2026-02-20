package Controller;

import DTO.AdminLoginRequest;
import DTO.UserLoginRequest;
import Service.LoginService;

// TODO: CRITICAL - Add Spring Boot dependencies to resolve @RestController, @RequestMapping, @PostMapping, @RequestBody, @Autowired
// Missing: spring-boot-starter-web in pom.xml or build.gradle
// Issue: All Spring annotations cannot be resolved - build system configuration required

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

