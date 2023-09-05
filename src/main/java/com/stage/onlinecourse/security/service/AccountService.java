package com.stage.onlinecourse.security.service;

import com.stage.onlinecourse.security.entities.AppRole;
import com.stage.onlinecourse.security.entities.AppUser;

public interface AccountService {
	AppUser addNewUser(String username, String password, String email, String ConfirmPassword);
	AppUser loadUserByUsername(String username);
	AppRole addNewRole(String role);
	void addRoleToUser(String username, String role);
	void removeRoleFromUser(String username, String role);
	
}
