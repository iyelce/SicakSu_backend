package com.example.demo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.Event;


public interface EventRepo extends MongoRepository<Event, String>{
	public List<Event> findByContent(String content);
	public List<Event> findByCreatedById(String userId);
    public List<Event> findByJoinedPeopleId(String userId);
}
