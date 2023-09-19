package com.stage.onlinecourse.controller.mongoDbController;

import java.util.List;

import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stage.onlinecourse.service.mongoDbService.MongoTrainingService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MongoTrainingController {
	MongoTrainingService mongoTrainingService;
	
	@PostMapping("/trainingManagement/createTraining")
	// TODO: verifier que l'utilisateur est un admin
    public ResponseEntity<Document> createTraining(@RequestParam(name = "trainingName",required = true) String trainingName,
    							@RequestParam(name = "description", required = true) String description){
		// Ajouter une image de description à la formation

        return mongoTrainingService.createTraining(trainingName,description);
    }
	
	@GetMapping("/listAllTraining")
	// Acessible à tous
	public ResponseEntity<List<Document>> listAllTraining() {
		
		return mongoTrainingService.listAllTraining();
	}
	
	@GetMapping("/{trainingName}/infos")
	// Acessible à tous
	public ResponseEntity<Document> listTrainingInfos(@PathVariable(required = true) String trainingName) {

		return mongoTrainingService.listTrainingInfos(trainingName);
	}
	
	@PutMapping("/{trainingCurrentName}/update")
    // TODO: verifier que l'utilisateur est un admin
    public ResponseEntity<Document> updateTraining(@PathVariable(required = true) String trainingCurrentName,
    							@RequestParam(name = "newName", required = true) String newName,
    							@RequestBody(required = true) String newDescription,
    							@RequestParam(name = "newPictureLink", required = true) String newPictureLink){
        return mongoTrainingService.updateTraining(trainingCurrentName, newName, newDescription, newPictureLink);
    }
	
	@DeleteMapping("/{trainingName}/delete")
    // TODO: verifier que l'utilisateur est un admin
    public ResponseEntity<Document> deleteTraining(@PathVariable(required = true) String trainingName){
		
		return mongoTrainingService.deleteTraining(trainingName);
    }
	
	@PostMapping("/training/{trainingName}/addFolder")
	//Verifier que c'est l'admin
	public ResponseEntity<Document> addFolderToTraining(@RequestParam(name = "folderName", required = true) String folderName,
														@PathVariable(required = true) String trainingName){
		
		return mongoTrainingService.addFolderToTraining(trainingName, folderName);
	}
	
	@GetMapping("/training/{trainingName}")
	//Accessible à tous
	public ResponseEntity<Document> listTrainingFolders(@PathVariable(required = true) String trainingName){
		
		return mongoTrainingService.listTrainingFolders(trainingName);
	}
	
	@PutMapping("/training/{trainingName}/updateFolder")
	// Verifier que c'est l'admin
	public ResponseEntity<Document> updateFolderOfTraining(@PathVariable(required = true) String trainingName,
																@RequestParam(name = "folderCurrentName", required = true) String folderCurrentName,
																@RequestParam(name = "folderNewName", required = true) String folderNewName){
		return mongoTrainingService.updateFolderOfTraining(trainingName, folderCurrentName, folderNewName);
	}
	
	/*TODO
	 * verifier que c'est l'admin*/
	@DeleteMapping("/training/{trainingName}/deleteFolder")
	public ResponseEntity<Document> deleteFolderOfTraining(@PathVariable(required = true) String trainingName,
																@RequestParam(name = "folderName", required = true) String folderName){
		
		//return mongoTrainingService.deleteFolderOfTraining(trainingName, folderName);
		return null;
	}
}
