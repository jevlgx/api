package com.stage.onlinecourse.security.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stage.onlinecourse.security.entities.AppRole;
import com.stage.onlinecourse.security.entities.AppUser;
import com.stage.onlinecourse.security.repository.AppRoleRepository;
import com.stage.onlinecourse.security.repository.AppUserRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	AppUserRepository appUserRepository;
	AppRoleRepository appRoleRepository;
	PasswordEncoder passwordEncoder;

	@Override
	public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
		AppUser appUser = appUserRepository.findByUsername(username);
		if(appUser!=null) throw new RuntimeException("this user already exits");
		if(!password.equals(confirmPassword)) throw new RuntimeException("Passwords not match");
		// TODO: ajouter le nécessaire pour vérifier l'email
		appUser = AppUser.builder()
				.userId(UUID.randomUUID().toString())
				.username(username)
				.password(passwordEncoder.encode(password))
				.email(email)
				.build();
		AppUser savedUser = appUserRepository.save(appUser);
		return savedUser;
	}

	@Override
	public AppRole addNewRole(String role) {
		AppRole appRole = appRoleRepository.findById(role).orElse(null);
		if(appRole != null) throw new RuntimeException("This Role already exit");
		appRole = AppRole.builder()
				.role(role)
				.build();
		AppRole savedAppRole = appRoleRepository.save(appRole);
		return savedAppRole;
	}

	@Override
	public void addRoleToUser(String username, String role) {
		AppUser appUser = appUserRepository.findByUsername(username);
		AppRole appRole = appRoleRepository.findById(role).orElse(null);
		if(appUser == null) throw new RuntimeException("This user don't exist");
		if(appRole == null) throw new RuntimeException("This Role don't exist");
		// TODO: renvoyer une erreur si l'utilisateur possède déja ce role
		if(appUser.getRoles().contains(new AppRole(role))) throw new RuntimeException("Cet utilisateur possède déjà ce role");
		appRole = AppRole.builder().
				role(role).
				build();
		appUser.getRoles().add(appRole);
		//appUserRepository.save(appUser);
	}

	@Override
	public void removeRoleFromUser(String username, String role) {
		// TODO Auto-generated method stub
		AppUser appUser = appUserRepository.findByUsername(username);
		AppRole appRole = appRoleRepository.findById(role).orElse(null);
		if(appUser == null) throw new RuntimeException("This user don't exist");
		if(appRole == null) throw new RuntimeException("This Role don't exist");
		// TODO: renvoyer une erreur si l'utilisateur ne possède pas ce role
		if(appUser.getRoles().contains(new AppRole(role))) throw new RuntimeException("Cet utilisateur ne possède pas ce role");
		appRole = AppRole.builder().
				role(role).
				build();
		appUser.getRoles().remove(appRole);
		//appUserRepository.save(appUser);
	}
	
//	@Override
	public AppUser loadUserByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}
	
}
