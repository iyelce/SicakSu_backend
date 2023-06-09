package com.example.demo.controller;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.mapping.DBRef;


import com.example.demo.models.Event;
import com.example.demo.models.Profile;
import com.example.demo.models.User;
import com.example.demo.payload.LoginPayload;
import com.example.demo.repo.EventRepo;
import com.example.demo.repo.ProfileRepo;
import com.example.demo.repo.UserRepo;

import jakarta.annotation.PostConstruct;


//TODO : add login function
@RestController
@RequestMapping("/sicaksu")
public class SicaksuController {
	
	
	@Autowired private ProfileRepo profileRepo;
	@Autowired private EventRepo eventRepo;
	@Autowired private UserRepo userRepo;

	
	private static final Logger logger = LoggerFactory.getLogger(SicaksuController.class);
	

	// creates a new user 
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		User repoUser = userRepo.findByUsername(user.getUsername());
        //check if there is an user with given user name if there 
		// is do not create anything and throw error and report that to user
		if (repoUser != null) {
			logger.error("Aha username var boyle");
            throw new CustomException("Username already exist");
        }
		//create profile instance first and get the id and then create the user
		Profile createdProfile = profileRepo.insert(user.getProfile());
		user.setProfile(createdProfile);
		
		//if there is not any user with given user name create new user with given credentials
		User createdUser = userRepo.insert(user);
		logger.info("User created : " + user.toString());
		
		return ResponseEntity.ok(createdUser);
	}
	
	//// new end points
	// login
	@PostMapping("/login")
	public ResponseEntity<?> createUser(@RequestBody LoginPayload loginUser) {
		
		User loginedUser = userRepo.findByUsernameAndPassword(loginUser.getUsername(),loginUser.getPassword());
        // check if there is a user with password and user name
		if(loginedUser == null) {
			throw new CustomException("Username or password is wrong");
		}
		logger.info("Logged In : " + loginedUser.toString());
		
		return ResponseEntity.ok(loginedUser);
	}
	
	// return the profiles list
	@GetMapping("/profile")
	public ResponseEntity<?> getAllProfiles(){
		return ResponseEntity.ok(profileRepo.findAll());
	}
	
	// return desired profile
	@GetMapping("/profile/{profileId}")
	public ResponseEntity<?> getProfileById(@PathVariable String profileId){
		Profile profile = profileRepo.findById(profileId).orElseThrow(()-> new CustomException("Profile not found"));
		return ResponseEntity.ok(profile);
	}
	
	// return all events list
	@GetMapping("/event")
	public ResponseEntity<?> getAllEvents(){
		return ResponseEntity.ok(eventRepo.findAll());
	}
	
	// return event info of the event id
	@GetMapping("/event/{eventId}")
	public ResponseEntity<?> getEventById(@PathVariable String eventId){
		Event event = eventRepo.findById(eventId).orElseThrow(()-> new CustomException("Event not found"));
		return ResponseEntity.ok(event);
	}
	
	// return the created events by the user
	@GetMapping("/event/created/{profileId}")
	public ResponseEntity<?> getProfileEvents(@PathVariable String profileId){
		Profile profile = profileRepo.findById(profileId).orElseThrow(()-> new CustomException("Profile not found"));
		List<Event> events = eventRepo.findByCreatedById(profileId);
		return ResponseEntity.ok(events);
	}
	
	// return the joined events by the user
	@GetMapping("/event/joined/{profileId}")
	public ResponseEntity<?> getProfileJoinedEvents(@PathVariable String profileId){
		Profile profile = profileRepo.findById(profileId).orElseThrow(()-> new CustomException("Profile not found"));
		List<Event> events = eventRepo.findByJoinedPeopleId(profileId);
		return ResponseEntity.ok(events);
	}
		
	
	// create new event for the user
	@PostMapping("/event/{profileId}")
	public ResponseEntity<?> createEvent(@RequestBody Event event, @PathVariable String profileId) {
		
		Profile profile = profileRepo.findById(profileId)
				.orElseThrow(() -> new CustomException("Profile not found"));
		event.setCreatedBy(profile);
		event.setJoinCount(0);
		
		Event eventSaved = eventRepo.insert(event);
		
		logger.info("Saved Event : " + event.toString());

		return ResponseEntity.ok(eventSaved);
	}
	
	
	// join an event while checking for the limit and whether the user is already in the event 
	// adds profile to event's joinedPeople list
	@PostMapping("/profile/{profileId}/event/{eventId}")
	public ResponseEntity<?> joinEvent(@PathVariable String profileId, @PathVariable String eventId) {
		
	    Profile profile = profileRepo.findById(profileId).orElseThrow(() -> new CustomException("Profile not found"));
	    Event event = eventRepo.findById(eventId).orElseThrow(() -> new CustomException("Event not found"));
    
        // add event to joinedEvents
        // add profile to joined People
        List<Profile> eventJoinedPeople = event.getJoinedPeople();
        if(event.getLimit() == event.getJoinCount()) {
        	throw new CustomException("Event capacity is full");
        }
        else if (event.getCreatedBy().getId().equals(profileId)) {
        	logger.info("this is your event");
        	throw new CustomException("You cannot join an event you created");
        }
        else if (!eventJoinedPeople.contains(profile)) {
        	eventJoinedPeople.add(profile);
            event.setJoinedPeople((ArrayList<Profile>) eventJoinedPeople);
            event.setJoinCount(event.getJoinCount() + 1);
            Event savedEvent = eventRepo.save(event);
            logger.info("Joined Event : " + event.toString() + profile.toString());
            return ResponseEntity.ok(savedEvent);
            
        } 
        else {
        	logger.info("alredy joined");
            throw new CustomException("Profile is already joined event");
        }
	}
	
	// delete a profile from a joined event
	@DeleteMapping("/profile/{profileId}/event/{eventId}")
	public ResponseEntity<?> leaveEvent(@PathVariable String profileId, @PathVariable String eventId) {
		
		Profile profile = profileRepo.findById(profileId).orElseThrow(() -> new CustomException("Profile not found"));
	    Event event = eventRepo.findById(eventId).orElseThrow(() -> new CustomException("Event not found"));
    
        // remove profile from event's joinedPeople
        List<Profile> eventJoinedPeople = event.getJoinedPeople();
        if (eventJoinedPeople.contains(profile)) {
            eventJoinedPeople.remove(profile);
            event.setJoinedPeople((ArrayList<Profile>) eventJoinedPeople);
            event.setJoinCount(event.getJoinCount() - 1);
            Event savedEvent = eventRepo.save(event);
            logger.info("left the event!!!");
            return ResponseEntity.ok(savedEvent);
        } else {
        	throw new CustomException("Profile is not exist the joined people");
        }
	} 
	
	
	// deletes the event with given id
	@DeleteMapping("/event/{eventId}")
	public ResponseEntity<?> deleteEvent(@PathVariable String eventId) {
	    // Find the event to be deleted
	    Event eventToDelete = eventRepo.findById(eventId).orElseThrow(() -> new CustomException("Event not found"));
	    // Delete the event
	    eventRepo.delete(eventToDelete);
	    return ResponseEntity.ok().body("Deleted");
	}
	

	@GetMapping("/hello")
	public String Hello(){
		return "HELLO";
	}
	
	////Errors
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public class CustomException extends RuntimeException {
	    private String message;

	    public CustomException(String message) {
	        this.message = message;
	    }

	    public String getMessage() {
	        return message;
	    }
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleUserNotFoundException(CustomException ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	
	
}