package com.stage.onlinecourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.stage.onlinecourse.security.service.AccountService;
import com.stage.onlinecourse.service.mongoDbService.MongoFileService;

import lombok.AllArgsConstructor;

@SpringBootApplication
public class ElearningApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ElearningApplication.class, args);
		System.out.println("000000000000000000000000");
	}
	
	//@Bean
	CommandLineRunner commandLineRunner(AccountService accountService) {
		return args -> {
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");
			
			accountService.addNewUser("user1", "az", "user1@gmail.com", "az");
			accountService.addNewUser("user2", "az", "user2@gmail.com", "az");
			accountService.addNewUser("admin", "az", "admin@gmail.com", "az");
			
			accountService.addRoleToUser("user1", "USER");
			accountService.addRoleToUser("user2", "USER");
			accountService.addRoleToUser("admin", "USER");
			accountService.addRoleToUser("admin", "ADMIN");
		};
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
