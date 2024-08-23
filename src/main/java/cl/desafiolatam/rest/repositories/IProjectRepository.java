package cl.desafiolatam.rest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.desafiolatam.rest.models.Project;
@Repository
public interface IProjectRepository extends JpaRepository<Project, Long>{
	List<Project> findByName(String name);
}
