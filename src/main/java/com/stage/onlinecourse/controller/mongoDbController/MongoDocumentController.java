package com.stage.onlinecourse.controller.mongoDbController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class MongoDocumentController {

	@PostMapping("/createDocument")
	// TODO: Verifier qu'il s'agit d'un enseignant
	public String createDocument(@RequestParam(name = "documentName", required = true) String requestBody) {
		// TODO: Verifier que cet enseignant a le droit de faire cette operation(l'admin lui a-t-il attribué ce dossier de travail ?)
		// TODO: creer le document
		return null;
	}
	
	@GetMapping("/document/read")
	// TODO: verifier = que c'est l'admin, l'enseignant qui a créé ce cours ou un élève ayant le droit d'acces à ce cours
	public String readDocument(@RequestParam(name = "documentId", required = true) int documentId) {
		// TODO: retourner le documznt en fonction de son type
		return null;
	}
	
	@PutMapping("/document/update")
	// TODO: Verifier qu'il s'agit d'un enseignant
	public String updateDocument(@RequestBody(required = true) String requestBody) {
		// TODO: Verifier que cet enseignant a le droit de faire cette operation(l'admin lui a-t-il attribué ce dossier de travail ?)
		return null;
	}
	
	@DeleteMapping("/document/delete")
	// TODO: Verifier qu'il s'agit de l'admin ou d'un enseignant
	public String deleteDocument(@RequestParam(name = "documentId", required = true) int documentId) {
		// TODO: Si c'est un enseignant, v"ridier qu'il a le droit de faire cette operation(l'admin lui a-t-il attribué ce dossier de travail ?)
		return null;
	}
}
