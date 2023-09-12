package com.stage.onlinecourse.security.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class SecurityController {
	
	@GetMapping("/login")
	// Tout le monde y a access
	public String login() {
		// TODO: verifier les informations de connexion
		// TODO: connecter l'utilisateur en fonction de son role
		return null;
	}
	
	@GetMapping("/logout")
	// Verifier que l'utilisateur est déjà connecté
	public String logout() {
		// TODO: déconnecter l'utilisateur
		return null;
	}
	
}
