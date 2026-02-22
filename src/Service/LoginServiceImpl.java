package Service;

import DTO.LoginResponse;
import Entity.Admin;
import Entity.User;
import Entity.LoginAttempt;
import Repository.AdminRepository;
import Repository.UserRepository;
import Repository.LoginAttemptRepository;
import Util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = Logger.getLogger(LoginServiceImpl.class.getName());
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCKOUT_DURATION_MINUTES = 15;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private LoginAttemptRepository loginAttemptRepo;

    @Override
    public LoginResponse userLogin(String email, String password, HttpServletRequest request) {
        String ipAddress = getClientIpAddress(request);
        
        // Check for brute force attempts
        if (isAccountLocked(email, "USER")) {
            logger.warning("Account locked due to multiple failed login attempts: " + email);
            return new LoginResponse(false, "Account temporarily locked. Try again later.");
        }

        User user = userRepo.findByEmail(email);

        if (user == null) {
            recordLoginAttempt(email, "USER", false, ipAddress);
            logger.warning("User not found: " + email);
            return new LoginResponse(false, "Invalid email or password");
        }

        if (!user.getPassword().equals(password)) {
            recordLoginAttempt(email, "USER", false, ipAddress);
            logger.warning("Incorrect password for user: " + email);
            return new LoginResponse(false, "Invalid email or password");
        }

        recordLoginAttempt(email, "USER", true, ipAddress);
        String token = JwtTokenUtil.generateToken(email, "USER");
        logger.info("Successful login: " + email);
        
        return new LoginResponse(true, "User Login Successful", token, "USER");
    }

    @Override
    public LoginResponse adminLogin(String username, String password, HttpServletRequest request) {
        String ipAddress = getClientIpAddress(request);
        
        // Check for brute force attempts
        if (isAccountLocked(username, "ADMIN")) {
            logger.warning("Admin account locked due to multiple failed login attempts: " + username);
            return new LoginResponse(false, "Account temporarily locked. Try again later.");
        }

        Admin admin = adminRepo.findByUsername(username);

        if (admin == null) {
            recordLoginAttempt(username, "ADMIN", false, ipAddress);
            logger.warning("Admin not found: " + username);
            return new LoginResponse(false, "Invalid username or password");
        }

        if (!admin.getPassword().equals(password)) {
            recordLoginAttempt(username, "ADMIN", false, ipAddress);
            logger.warning("Incorrect password for admin: " + username);
            return new LoginResponse(false, "Invalid username or password");
        }

        recordLoginAttempt(username, "ADMIN", true, ipAddress);
        String token = JwtTokenUtil.generateToken(username, "ADMIN");
        logger.info("Successful admin login: " + username);
        
        return new LoginResponse(true, "Admin Login Successful", token, "ADMIN");
    }

    private void recordLoginAttempt(String identifier, String userType, boolean success, String ipAddress) {
        LoginAttempt attempt = new LoginAttempt(identifier, userType, success, ipAddress);
        loginAttemptRepo.save(attempt);
    }

    private boolean isAccountLocked(String identifier, String userType) {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(LOCKOUT_DURATION_MINUTES);
        long failedAttempts = loginAttemptRepo.findFailedAttempts(identifier, startTime).size();
        return failedAttempts >= MAX_FAILED_ATTEMPTS;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}

