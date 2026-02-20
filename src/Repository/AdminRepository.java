package Repository;

import Entity.Admin;

// TODO: Implement repository - either extend JpaRepository or provide concrete implementation
// FIXME: Missing spring-boot-starter-data-jpa dependency for JPA support

public interface AdminRepository {
    Admin findByUsername(String username);
    Admin findByEmail(String email);
    Admin save(Admin admin);
    Admin findById(Long id);
    void delete(Admin admin);
}
