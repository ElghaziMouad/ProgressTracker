package com.mouadProject.projectManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouadProject.projectManager.domain.Project;
import com.mouadProject.projectManager.repositories.ProjectRepository;
import com.mouadProject.projectManager.services.ProjectService;
import java.util.List;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository repo;
	
	public Project getOne(Long id){
		return repo.findProjectById(id);
	}

	public List<Project> findAll(){
		return repo.findAll();
	}
	
	public Project save(Project obj){
		try {
			obj.setName(obj.getName());
			return repo.save(obj);
		} catch(Exception ex) {
			return null;
		}
		
	}
	
	public Project update(Project obj){
		return repo.save(obj);
	}
	
	public void deleteById(Long id){
		repo.deleteById(id);
	}
	
}
