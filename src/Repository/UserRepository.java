package Repository;

import Entity.User;

// TODO: Implement repository - either extend JpaRepository or provide concrete implementation  
// FIXME: Missing spring-boot-starter-data-jpa dependency for JPA support

public interface UserRepository {
    User findByEmail(String email);
    User save(User user);
    User findById(Long id);
    void delete(User user);
}

