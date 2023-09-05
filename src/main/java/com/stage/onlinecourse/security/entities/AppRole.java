package com.stage.onlinecourse.security.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Data  @NoArgsConstructor @AllArgsConstructor @Builder
public class AppRole {
	@Id
	private String role;
}
