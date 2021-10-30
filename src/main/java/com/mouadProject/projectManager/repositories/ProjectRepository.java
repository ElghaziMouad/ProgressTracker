package com.mouadProject.projectManager.repositories;

import com.mouadProject.projectManager.domain.Project;
import com.mouadProject.projectManager.domain.TimeHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
	Project findProjectById(Long id);
	
	
}
