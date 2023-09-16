package com.stage.onlinecourse.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Training {

	int id;
	@Builder.Default
	String type = "training";
	String name;
	String description;
	@Builder.Default
	String pictureLink = "";
	@Builder.Default
	ArrayList<Integer> children = new ArrayList<Integer>();
	
	public Document toDocument() {
        Document document = new Document()
        		.append("id", this.id)
        		.append("type", this.id)
        		.append("name", this.name)
        		.append("description", this.description)
        		.append("pictureLink", this.pictureLink)
        		.append("children", this.children);
        return document;
    }
	
}
