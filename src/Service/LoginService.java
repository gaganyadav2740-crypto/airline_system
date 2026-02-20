package Service;

public interface LoginService {
    String userLogin(String email, String password);
    String adminLogin(String username, String password);
}
