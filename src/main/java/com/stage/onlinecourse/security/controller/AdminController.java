package com.stage.onlinecourse.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stage.onlinecourse.service.mongoDbService.MongoFileService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AdminController {

	@PostMapping("admin/update")
	// TODO: verifier que c'est bien un admin
	public String updateAdmin(@RequestBody String requestBody) {
		/* TODO: verifier la validité des informations
		 * Mettre à jours les infos de l'admin si tout est Ok
		 * Renvoyer un message d'erreur en cas d'erreur*/
		return null;
	}
	
	
}
