package com.mouadProject.projectManager.repositories;

import com.mouadProject.projectManager.domain.TimeHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TimeHistoryRepository extends JpaRepository<TimeHistory, Long>{
	TimeHistory findTimeHistoryById(Long id);
	
	List<TimeHistory> findAllByProjectId(Long projectId);
	
}
