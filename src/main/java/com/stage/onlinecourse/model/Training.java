package com.stage.onlinecourse.model;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mongodb.client.MongoCollection;
import com.stage.onlinecourse.security.MongoDbConnexion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Training {

	String id;
	@Builder.Default
	String type = "training";
	String name;
	String description;
	@Builder.Default
	String pictureLink = "";
	
	@Qualifier("trainingCollection")
	public static MongoCollection<Document> trainingCollection = new MongoDbConnexion().trainingCollection;
	@Qualifier("folderCollection")
	public MongoCollection<Document> folderCollection;
	
	public static Training documentToTraining(Document document) {
		Training training = Training.builder()
				.id(document.get("_id").toString())
				.name(document.getString("name"))
				.description(document.getString("description"))
				.pictureLink(document.getString("pictureLink"))
				.build();
		return training;
	}
	
	public Document toDocument() {
        Document document = new Document()
        		.append("name", this.name)
        		.append("description", this.description)
        		.append("pictureLink", this.pictureLink);
        return document;
    }

	public static Training getTrainingByName(String trainingName) {
		Document document = trainingCollection.find(new Document("name", trainingName)).first();
		if(document == null) throw new RuntimeException("La formation "+trainingName+" n'existe pas");
		System.out.println("333333333");
		Training training = documentToTraining(document);
		System.out.println("4444444444");
		return training;
	}
	
	public boolean hasFolder(String folderName) {
		Document folderDocument = folderCollection.find(new Document("parentId", id).append("name", folderName)).first();
		if(folderDocument == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public static boolean exist(String trainingName) {
		Document document = Training.trainingCollection.find(new Document("name", trainingName)).first();
		if(document == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean equals(Training training) {
		if(name == training.name && description == training.description && pictureLink == training.pictureLink) {
			return true;
		}else {
			return false;
		}
	}
}
