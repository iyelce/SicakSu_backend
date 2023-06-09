package com.example.demo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Profile;
import com.example.demo.models.User;

@Repository
public interface UserRepo extends MongoRepository<User, String>{
	public User findByUsername(String username);
	public User findByUsernameAndPassword(String username, String password);


}
