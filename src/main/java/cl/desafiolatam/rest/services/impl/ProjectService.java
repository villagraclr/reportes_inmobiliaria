package cl.desafiolatam.rest.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.desafiolatam.rest.repositories.IProjectRepository;
import cl.desafiolatam.rest.services.IProjectService;
import cl.desafiolatam.rest.models.Project;
@Service
public class ProjectService implements IProjectService {
	@Autowired
    private IProjectRepository projectRepository;

    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project findProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public List<Project> findProjectsByName(String name) {
        return projectRepository.findByName(name);
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
