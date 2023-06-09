package com.example.demo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Profile;

@Repository
public interface ProfileRepo extends MongoRepository<Profile, String>{
	//public List<Profile> findByUsername(String name);
}