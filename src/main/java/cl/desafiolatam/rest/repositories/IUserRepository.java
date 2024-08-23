package cl.desafiolatam.rest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.desafiolatam.rest.models.User;

public interface IUserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String name);
	public boolean existsByUsername(String name);
}
