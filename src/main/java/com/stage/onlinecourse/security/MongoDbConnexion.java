package com.stage.onlinecourse.security;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Configuration
public class MongoDbConnexion {
	private String mongoClientURI = "mongodb://localhost:27017/";
	private String databaseName = "baseTest";
	private String trainingCollectionName = "trainingCollection";
	private String folderCollectionName = "folderCollection";
	
	MongoClient mongoClient = MongoClients.create(mongoClientURI);
	MongoDatabase database = mongoClient.getDatabase(databaseName);
	public MongoCollection<Document> trainingCollection = database.getCollection(trainingCollectionName);
	public MongoCollection<Document> folderCollection = database.getCollection(folderCollectionName);
	
	@Qualifier("trainingCollection")
	MongoCollection<Document> trainingCollection(){
		return trainingCollection;
	}
	
	@Qualifier("folderCollection")
	MongoCollection<Document> folderCollection(){
		return folderCollection;
	}
}
