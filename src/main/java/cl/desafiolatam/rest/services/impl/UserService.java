package cl.desafiolatam.rest.services.impl;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cl.desafiolatam.rest.models.User;
import cl.desafiolatam.rest.models.Role;
import cl.desafiolatam.rest.repositories.IRoleRepository;
import cl.desafiolatam.rest.repositories.IUserRepository;
import cl.desafiolatam.rest.services.IUserService;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(List.of(userRole)); // Assign only the "ROLE_USER"
        userRepository.save(user);
    }

    @Override
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(List.of(adminRole)); // Assign only the "ROLE_ADMIN"
        userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}

