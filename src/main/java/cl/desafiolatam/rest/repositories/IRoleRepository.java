package cl.desafiolatam.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.desafiolatam.rest.models.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}