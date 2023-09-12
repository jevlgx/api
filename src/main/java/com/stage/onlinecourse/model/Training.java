package com.stage.onlinecourse.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.String;

import org.bson.Document;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training {
	private String name;
	private String description;
	private Map<String, Folder> children;
	
	public Document toJSON() {
		Document document = new Document("name", name)
                .append("description", description)
                .append("children", new HashMap<String, Folder>());
		return document;
	}
}
