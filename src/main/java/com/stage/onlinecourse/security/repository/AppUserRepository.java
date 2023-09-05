package com.stage.onlinecourse.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stage.onlinecourse.security.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String>{
	AppUser findByUsername(String username);
}
