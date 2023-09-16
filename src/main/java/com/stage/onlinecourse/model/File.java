package com.stage.onlinecourse.model;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class File {

	int id;
	@Builder.Default
	String type = "document";
	String name;
	String description;
	int parentId;
	@Builder.Default
	String storageLink = "";
	
	public Document toDocument() {
        Document document = new Document()
        		.append("id", this.id)
        		.append("type", this.type)
        		.append("name", this.name)
        		.append("description", this.description)
        		.append("parentId", this.parentId)
        		.append("storageLink", this.storageLink);
        return document;
    }
}
