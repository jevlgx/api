package com.stage.onlinecourse.security.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class TeacherController {
	
	@GetMapping("/teachersManagement")
	public String showTeachers() {
		// TODO: retourner sous forme de Json les informations nécessaires pour la page de gestion des enseignants
		return null;
	}

	@PutMapping("/teachersManagement/assignFolder")
	// TODO: verifier que l'utilisateur est un admin
	public String assignFolder(@RequestBody(required = true) String requestBody) {
		// TODO implementer
		return null;
	}	
	
	@PostMapping("/teachersManagement/createTeacher")
	// TODO: verifier que l'utilisateur est un admin
	public String createTeacher(@RequestBody(required = true) String requestBody) {
		// un mot de passe par defaut est généré pour chaque enseignat crée. l'Admin est chargé de transmettre ce mot de passe à l'enseignant
		// TODO implementer
		return null;
	}
	
	@DeleteMapping("/teachersManagement/deleteTeacher")
	// TODO: verifier que l'utilisateur est un admin
	public String deleteTeacher(@RequestParam(name = "teacherId", required = true) int teacherId) {
		// TODO implementer
		return null;
	}
	
	// **** OPERATIONS PROPRES AUX ENSEIGNANTS **** //

	@GetMapping("/teachersInfos")
	// TODO: verifier que l'utilisateur est un enseignant(pas admin)
	public String getTeachersInfos(@RequestParam(name = "teacherId", required = true) int teachersId) {
		// TODO implementer
		return null;
	}
	
	@PutMapping("/updatePassword")
	// TODO: verifier que l'utilisateur est un enseignant(pas Admin)
	public String updateTeacherPassword(@RequestBody(required = true) String requestBody) {
		// TODO implementer
		return null;
	}
	
}
