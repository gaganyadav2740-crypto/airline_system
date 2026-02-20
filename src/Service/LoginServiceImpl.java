package Service;

import Entity.Admin;
import Repository.AdminRepository;

@Service
public class LoginServiceImpl implements LoginService{


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Override
    public String userLogin(String email, String password) {

        User user = userRepo.findByEmail(email);

        if (user == null)
            return "User not found";

        if (!user.getPassword().equals(password))
            return "Incorrect password";

        return "User Login Successful";
    }

    @Override
    public String adminLogin(String username, String password) {

        Admin admin = adminRepo.findByUsername(username);

        if (admin == null)
            return "Admin not found";

        if (!admin.getPassword().equals(password))
            return "Incorrect password";

        return "Admin Login Successful";
    }
}

