package cl.desafiolatam.rest.services;

import java.util.List;
import cl.desafiolatam.rest.models.Project;

public interface IProjectService {
    List<Project> findAllProjects();
    Project findProjectById(Long id);
    List<Project> findProjectsByName(String name);
    Project saveProject(Project project);
    void deleteProject(Long id);
}
