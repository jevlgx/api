package com.stage.onlinecourse.security.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class UserController {
	
	@PostMapping("user/createUser")
	// TODO: Verifier que c'est un visiteur
	public String createUser(@RequestBody(required = true) String requestBody) {
		// TODO: Implémenter
		return null;
	}
	
	@GetMapping("user/getUsers")
	// TODO: Verifier qu'il s'agit d'un admin
	public String getUsers() {
		// TODO: Retourner la liste des utilisateurs
		return null;
	}
	
	@GetMapping("/getUserInformations")
	// TODO: Verifier qu'il s'agit de l'utilisateur inscrit
	public String getUserInformations(@RequestParam(name = "userId", required = true) int userId) {
		// TODO: Retourner les informations demandées pas l'utilisateur
		return null;
	}
	
	@PutMapping("/updateUser")
	// Verifier qu'il s'agit de l'utilisateur correspondant
	public String updateUser() {
		// TODO: traiter la requette et mettre à jour les informations de l'utilisateur
		return null;
	}
	
	@PutMapping("/registerForATraining")
	// TODO: Vérifier que la requette vient d"un Utilisateur inscrit et ayant le droit de s'inscrire
	public String registerForATraining() {
		// TODO: verifier les informations d'enregistrement au cours et ajout du cours à la liste des cours de l'utilisateur
		return null;
	}
	
	@DeleteMapping("/deleteUser")
	// TODO: Verifier qu'il s'agit de l'utilisateur inscrit
	public String deleteUser(@RequestParam(name = "userId", required = true) int userId) {
		// TODO: supprimer le compte de l'utilisateur
		return null;
	}
	
}
