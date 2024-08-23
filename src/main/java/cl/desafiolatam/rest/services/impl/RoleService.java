package cl.desafiolatam.rest.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.desafiolatam.rest.models.Role;
import cl.desafiolatam.rest.repositories.IRoleRepository;
import cl.desafiolatam.rest.services.IRoleService;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}