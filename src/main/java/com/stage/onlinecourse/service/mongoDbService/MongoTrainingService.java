package com.stage.onlinecourse.service.mongoDbService;

import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.stage.onlinecourse.model.Training;
import com.stage.onlinecourse.security.MongoDbConnexion;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Component
public class MongoTrainingService {
	
	@Qualifier("tainingCollection")
	public MongoCollection<Document> trainingCollection;
	public Training training;
	
	public String createTraining(String name,String description) {
		
		/*TODO:
		 * verifier que ces parametres sont non nuls
		 * traiter les potentielles failles de securité
		 * */
		
		Document trainingJSON;
		Optional<Document> createdTraining;
		
		training = Training.builder()
				.name(name)
				.description(description)
				.build();
		createdTraining =  Optional.ofNullable(trainingCollection.find(new Document("name", name)).first());
		
		if(createdTraining.isPresent()) {
			throw new RuntimeException("Une formation avec le même nom existe déjà");
		}else {
			// Enregistrer la formation
			trainingCollection.insertOne(training.toJSON());
		}
		return null;
	}
	
}
