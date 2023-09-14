package com.stage.onlinecourse.security.repository;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import lombok.AllArgsConstructor;

public interface MongoDbRepository {
	
	// Replace the placeholder with your MongoDB deployment's connection string
	String uri = "mongodb://localhost:27017/";
	public default MongoClient mongoClient() {
		try (MongoClient mongoClient = MongoClients.create(uri)) {
			MongoDatabase database = mongoClient.getDatabase("test");
			MongoCollection<Document> collection = database.getCollection("col1");
			
			Document doc = collection.find(eq("name", "collection3")).first();
			if (doc != null) {
				System.out.println(doc.toJson());
			} else {
				System.out.println("No matching documents found.");
			}
		}catch (Exception e) {
			throw new RuntimeException("Erreur de connexion à la base de données distante");
		}
		return MongoClients.create(uri);
	}
		
}
