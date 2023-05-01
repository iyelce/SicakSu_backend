package com.example.demo;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.repo.EventRepo;
import com.example.demo.repo.ProfileRepo;

import org.slf4j.Logger;

@SpringBootApplication
public class SicakSuApplication implements CommandLineRunner{
	
	@Autowired ProfileRepo userRepo;
	@Autowired EventRepo eventRepo;
	
	private static final Logger logger 
	= (Logger) LoggerFactory.getLogger(SicakSuApplication.class);
	

	public static void main(String[] args) {
		SpringApplication.run(SicakSuApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
