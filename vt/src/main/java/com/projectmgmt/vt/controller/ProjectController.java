package com.projectmgmt.vt.controller;

import com.projectmgmt.vt.model.Project;
import com.projectmgmt.vt.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProjectController {

	@Autowired
	ProjectRepository projectRepository;

	@GetMapping("/projects")
	public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false) String title) {
		try {
			List<Project> projectsList = new ArrayList<Project>();

			if (title == null)
				projectRepository.findAll().forEach(projectsList::add);
			else
				projectRepository.findByTitleContaining(title).forEach(projectsList::add);

			if (projectsList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(projectsList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/projects/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable("id") long id) {
		Optional<Project> projectData = projectRepository.findById(id);

		if (projectData.isPresent()) {
			return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/projects")
	public ResponseEntity<Project> createProject(@RequestBody Project project1) {
		try {
			Project project = projectRepository
					.save(new Project(project1.getTitle(), project1.getDescription(), false));
			return new ResponseEntity<>(project, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/projects/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestBody Project project) {
		Optional<Project> projectData = projectRepository.findById(id);

		if (projectData.isPresent()) {
			Project _project = projectData.get();
			_project.setTitle(project.getTitle());
			_project.setDescription(project.getDescription());
			_project.setPublished(project.isPublished());
			return new ResponseEntity<>(projectRepository.save(_project), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/projects/{id}")
	public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") long id) {
		try {
			projectRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/projects")
	public ResponseEntity<HttpStatus> deleteAllProjects() {
		try {
			projectRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/projects/published")
	public ResponseEntity<List<Project>> findByPublished() {
		try {
			List<Project> projects = projectRepository.findByPublished(true);

			if (projects.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(projects, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
