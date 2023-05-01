package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Event;
import com.example.demo.repo.EventRepo;
import com.example.demo.repo.ProfileRepo;

@RestController
@RequestMapping("/events")
public class PostController {

	@Autowired EventRepo eventRepo;
	@Autowired ProfileRepo proRepo;
	
	@GetMapping("/posts")
	public List<Event> getAllEvents(){
		
		List<Event> prods = eventRepo.findAll();
		return prods;
		
	}
	
	@GetMapping("/posts/{headline}")
	public List<Event> getEventsByName(@PathVariable("headline") String headline){
		
		List<Event> events = eventRepo.findByHeadline(headline);
		return events;
	}
	
	
	
	
	
	
}
