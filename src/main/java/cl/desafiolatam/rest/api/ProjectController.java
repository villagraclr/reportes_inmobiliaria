package cl.desafiolatam.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.desafiolatam.rest.models.Project;
import cl.desafiolatam.rest.services.IProjectService;

@RestController
@RequestMapping("/api/v1/projects")  // Asegúrate de que el path sea correcto
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.findAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.findProjectById(id);
        if (project != null) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectService.saveProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        Project existingProject = projectService.findProjectById(id);
        if (existingProject != null) {
            project.setId(id);
            Project updatedProject = projectService.saveProject(project);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Project existingProject = projectService.findProjectById(id);
        if (existingProject != null) {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
