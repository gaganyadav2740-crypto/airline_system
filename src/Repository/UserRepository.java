package Repository;

import Entity.Admin;
import Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
}

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}

