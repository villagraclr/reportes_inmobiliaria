package cl.desafiolatam.rest.services;

import java.util.List;
import cl.desafiolatam.rest.models.User;

public interface IUserService {
    void saveWithUserRole(User user);
    void saveUserWithAdminRole(User user);
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
