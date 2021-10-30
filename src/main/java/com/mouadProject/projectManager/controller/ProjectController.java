package com.mouadProject.projectManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.mouadProject.projectManager.domain.Project;
import com.mouadProject.projectManager.services.ProjectService;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService serv;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable String id){
		return new ResponseEntity<Project>(serv.getOne(Long.parseLong(id)), HttpStatus.OK);
	}

	@GetMapping("/all")
	public List<Project> getAll(){
		return serv.findAll();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<?> insertOne(@Valid @RequestBody Project obj, BindingResult result) {
		Project newObj = serv.save(obj);
		if(newObj == null) {
			return new ResponseEntity<String>("repeated name", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Project>(newObj, HttpStatus.CREATED);
	
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOne(@PathVariable String id) {
		serv.deleteById(Long.parseLong(id));
		return new ResponseEntity<String>("Project with id: '"+id+"' is deleted", HttpStatus.OK);
	}

	
}
