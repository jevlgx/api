package com.stage.onlinecourse.model;

import java.util.List;
import java.util.Map;
import java.lang.String;

import org.springframework.stereotype.Component;

import aj.org.objectweb.asm.Type;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Data
@Builder
public class Folder {
	@Builder.Default
	private final String type = "folder";
	private String logicalDocId;
	private Map<String, Folder> children;
}
