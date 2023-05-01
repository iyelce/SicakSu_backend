package com.example.demo.repo;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.Event;
import com.example.demo.models.Profile;



public interface EventRepo extends MongoRepository<Event, String>{
		
	public List<Event> findByHeadline(String name);
		
	public List<Event> findByUser(Profile user);

	

}
