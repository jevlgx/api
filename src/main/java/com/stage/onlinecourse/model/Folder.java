package com.stage.onlinecourse.model;

import java.lang.String;
import java.util.ArrayList;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


public class Folder extends Ressource{
	 
	String id;
	@Builder.Default
	String type = "folder";
	String name;
	String description;
	String storageLink;
 Document listChildren() {
		return null;
	}
	
	public ArrayList<Folder> getFolders(){
		return null;
	}
	public ArrayList<Folder> getFiles(){
		return null;
	}
	public ArrayList<Ressource> listContaint(){
		return null;
	}

}
