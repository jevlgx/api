package com.stage.onlinecourse.security.controller;

import org.springframework.web.bind.annotation.PostMapping;

public class VisitorController {

	@PostMapping("/createAccount")
	// Acessible à tous
	public String createAccount() {
		/* TODO: verifier les informations de creations de compte
		 * Creer le comte et notifier l'utilisateur si tout se passe bien
		 * Renvoyer un message d'erreur s'il y a une erreur dans le formulaire de creation de compte*/
		return null;
	}
	
}
