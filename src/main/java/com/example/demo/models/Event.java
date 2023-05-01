package com.example.demo.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.time.LocalDateTime;

public class Event {

	@Id private String id;
	private String content;
	private String headline;
	private int limit;
	private int joinCount;
	private LocalDateTime requestDate;      
	
	private ArrayList<Profile> joinRequests = new ArrayList<Profile>();
	private ArrayList<Profile> joinedPeople = new ArrayList<Profile>();
	
	@DBRef private Profile user;

	public Event() {}
	
	public Event(String content, String headline, int limit, LocalDateTime requestDate) {
		super();
		this.content = content;
		this.headline = headline;
		this.limit = limit;
		this.requestDate = requestDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getJoinCount() {
		return joinCount;
	}

	public void setJoinCount(int joinCount) {
		this.joinCount = joinCount;
	}

	public ArrayList<Profile> getJoinRequests() {
		return joinRequests;
	}

	public void setJoinRequests(ArrayList<Profile> joinRequests) {
		this.joinRequests = joinRequests;
	}

	public ArrayList<Profile> getJoinedPeople() {
		return joinedPeople;
	}

	public void setJoinedPeople(ArrayList<Profile> joinedPeople) {
		this.joinedPeople = joinedPeople;
	}

	public Profile getUser() {
		return user;
	}

	public void setUser(Profile user) {
		this.user = user;
	}
	
	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public void setResponseDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}
}
