package com.stage.onlinecourse.controller.mongoDbController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	static String response;
	
	@PostMapping("/createTraining")
	// TODO: verifier que l'utilisateur est un admin
    public String createTraining(@RequestParam(name = "name",required = true) String name,
    							@RequestParam(name = "description", required = true) String description){
		// @RequestParam(name = "picture", required = true) String picture
		response = mongoTrainingService.createTraining(name,description);
		// TODO implementer
        return response;
    }
	
	@GetMapping("/listAllTraining")
	// Acessible à tous
	public String listTraining() {
		// TODO: renvoyer les informations de la formation
		return null;
	}
	
	@GetMapping("/listTrainingInfos")
	// Acessible à tous
	public String listTrainingInfos(@RequestParam(name = "trainingName", required = true) String trainingName) {
		// TODO: renvoyer les informations de la formation
		return null;
	}
	
	@PutMapping("/updateTraining")
    // TODO: verifier que l'utilisateur est un admin
    public String updateTraining(@RequestBody(required = true) String requestBody){
		// TODO implementer
        return null;
    }
	
	@DeleteMapping("/deleteTraining")
    // TODO: verifier que l'utilisateur est un admin
    public String deleteTraining(@RequestParam(name = "trainingName", required = true) int trainingName){
		// TODO implementer
        return null;
    }
	
}
