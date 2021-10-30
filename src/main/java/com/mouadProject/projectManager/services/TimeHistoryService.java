package com.mouadProject.projectManager.services;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouadProject.projectManager.domain.TimeHistory;
import com.mouadProject.projectManager.repositories.ProjectRepository;
import com.mouadProject.projectManager.repositories.TimeHistoryRepository;
import com.mouadProject.projectManager.services.TimeHistoryService;

import java.util.Date;
import java.util.List;

@Service
public class TimeHistoryService {

	@Autowired
	private TimeHistoryRepository repo;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public TimeHistory getOne(Long id){
		return repo.findTimeHistoryById(id);
	}

	public List<TimeHistory> findAll(){
		return repo.findAll();
	}
	
	public TimeHistory save(TimeHistory obj){
		return repo.save(obj);
	}
	
	public TimeHistory update(TimeHistory obj){
		return repo.save(obj);
	}
	
	public void deleteById(Long id){
		repo.deleteById(id);
	}
	
	public List<TimeHistory> findAllByProjectId(Long projectId) {
		return repo.findAllByProjectId(projectId);
	}

	public void goNext(Long id) {
		List<TimeHistory> arr = repo.findAllByProjectId(id);
		LocalDateTime dateTime = LocalDateTime.now(DateTimeZone.forID("Europe/Madrid"));
		if(arr.size() > 0) {
			TimeHistory obj = arr.get(arr.size()-1);
			if(obj.getEndDate() != null) {
				TimeHistory newObj = new TimeHistory();
				newObj.setProject(obj.getProject());
				newObj.setStartDate(dateTime.toDate());
				repo.save(newObj);
			}else {
				obj.setEndDate(dateTime.toDate());
				long diffInMillies = Math.abs(dateTime.toDate().getTime() - obj.getStartDate().getTime());
				obj.setDuration(diffInMillies);
				repo.save(obj);
			}
			
		} else {
			TimeHistory newObj = new TimeHistory();
			newObj.setStartDate(dateTime.toDate());
			newObj.setProject(projectRepository.getById(id));
			repo.save(newObj);
		}
		
	}
}
