package com.mouadProject.projectManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.mouadProject.projectManager.domain.TimeHistory;
import com.mouadProject.projectManager.services.TimeHistoryService;
import java.util.List;

@RestController
@RequestMapping("/api/timeHistory")
public class TimeHistoryController {

	@Autowired
	private TimeHistoryService serv;
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable String id){
		return new ResponseEntity<TimeHistory>(serv.getOne(Long.parseLong(id)), HttpStatus.OK);
	}

	@GetMapping("/all")
	public List<TimeHistory> getAll(){
		return serv.findAll();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<?> insertOne(@Valid @RequestBody TimeHistory obj, BindingResult result) {
	
		return new ResponseEntity<TimeHistory>(serv.save(obj), HttpStatus.CREATED);
	
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOne(@PathVariable String id) {
		serv.deleteById(Long.parseLong(id));
		return new ResponseEntity<String>("TimeHistory with id: '"+id+"' is deleted", HttpStatus.OK);
	}
	
	@GetMapping("/project/{projectId}")
	public List<TimeHistory> getAll(@PathVariable String projectId){
		return serv.findAllByProjectId(Long.parseLong(projectId));
	}
	
	@PostMapping("/project/{projectId}/next")
	public void startWork(@PathVariable String projectId){
		serv.goNext(Long.parseLong(projectId)); 
	}

	
}
