package Service;

import DTO.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
    LoginResponse userLogin(String email, String password, HttpServletRequest request);
    LoginResponse adminLogin(String username, String password, HttpServletRequest request);
}
