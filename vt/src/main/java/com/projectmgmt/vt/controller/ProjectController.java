package com.projectmgmt.vt.controller;

import com.projectmgmt.vt.model.Project;
import com.projectmgmt.vt.repository.ProjectRepository;
import com.projectmgmt.vt.service.ManageProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProjectController {

	@Autowired
	ManageProjectService manageProjectService;

	@Autowired
	ProjectRepository projectRepository;

	@PostMapping("/projects")
	public ResponseEntity<Project> createProject(@RequestBody Project project1) {
		return manageProjectService.createProject(project1);
	}

	@GetMapping("/projects")
	public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false) String title) {
		return manageProjectService.getAllProjects();
	}

	@GetMapping("/projects/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable("id") long id) {
		return manageProjectService.getProjectById(id);
	}

	@PutMapping("/projects/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestBody Project project) {
		return manageProjectService.updateProject(id,project);
	}

	@DeleteMapping("/projects/{id}")
	public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") long id) {
		return manageProjectService.deleteProject(id);
	}

	@DeleteMapping("/projects")
	public ResponseEntity<HttpStatus> deleteAllProjects() {
		return manageProjectService.deleteAllProjects();
	}

	@GetMapping("/projects/published")
	public ResponseEntity<List<Project>> findByPublished() {
		return manageProjectService.findByPublished();
	}

}
