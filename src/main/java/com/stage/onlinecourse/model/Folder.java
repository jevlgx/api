package com.stage.onlinecourse.model;

import java.util.List;
import java.util.Map;
import java.lang.String;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class Folder {
	private String name;
	private Map<String, Folder> folderChildren;
}
