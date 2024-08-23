package cl.desafiolatam.rest.services;

import cl.desafiolatam.rest.models.Role;

public interface IRoleService {
    Role findByName(String roleName);
}
