package com.stage.onlinecourse.controller.mongoDbController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoFolderController {

	@PostMapping("/createFolder")
	// TODO: verifier que l'utilisateur est un admin
	public String createFolder(@RequestBody(required = true) String requestBody) {
		/*le requesBody doit comprendre
		 * chemin de creation |type = "folder"|description|nom|photo|*/
		// TODO implementer
		return null;
	}
	
	@GetMapping("/listFolderInfos")
	// Accessible à tous
	public String listFolderInfos(@RequestParam(name = "folderId", required = true) int folderId) {
		// Retourner les informations du dossier en fonction des droits du demendeur
		return null;
	}
	
	@PutMapping("/folder/update")
	// TODO: verifier que l'utilisateur est un admin
	public String updateFolder(@RequestBody(required = true) String requestBody) {
		// TODO implementer
		return null;
	}
	
	@DeleteMapping("/folder/delete")
	// TODO: verifier que l'utilisateur est un admin
	public String deleteFolder(@RequestParam(name = "folderId", required = true) int folderId) {
		// TODO implementer
		return null;
	}
	
	@PutMapping("/folder/move")
	// TODO: verifier que l'utilisateur est un admin
	public String moveFolder(@RequestBody(required = true) String requestBody) {
		// TODO implementer
		return null;
	}
	
}
