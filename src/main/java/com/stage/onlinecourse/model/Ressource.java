package com.stage.onlinecourse.model;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ressource {
	
	String id;
	@Builder.Default
	String type = "folder";
	String name;
	String description;
	String storageLink;
	
	
	public Document toDocument() {
        Document document = new Document()
        		.append("name", name)
        		.append("description", description)
        		.append("storageLink", storageLink);
        return document;
    }
	
	public boolean exitAt(Path ressourcePath) {
		return false;
	}
	public static Ressource getRessourceByPath(Path ressourcePath) {
		return null;
	}
	
	public Path addToDirectory(Path ressourcePath) {
		return null;
	}
	public boolean equals(Ressource ressource) {
		return false;
	}
	public Path moveTo(Path destinationPath) {
		return null;
	}
	public Path delete() {
		return null;
	}
	public Ressource updateTo(Ressource ressource) {
		return  null;
	}
	
}
