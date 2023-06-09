package com.example.demo;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//import com.example.demo.config.Configure;
import com.example.demo.models.Profile;
import com.example.demo.repo.EventRepo;
import com.example.demo.repo.ProfileRepo;

import org.slf4j.Logger;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.example")
@EnableMongoRepositories(basePackages = "com.example.demo.repo")
public class SicakSuApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicakSuApplication.class, args);
	}

}
