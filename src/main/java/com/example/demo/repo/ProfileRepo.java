package com.example.demo.repo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.models.Profile;

public interface ProfileRepo extends MongoRepository<Profile, String>{
	
	public List<Profile> findByNameLikeIgnoreCase(String name);
}