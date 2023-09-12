package com.stage.onlinecourse.service.mongoDbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stage.onlinecourse.security.repository.AppRoleRepository;
import com.stage.onlinecourse.security.repository.AppUserRepository;
import com.stage.onlinecourse.security.repository.MongoDbRepository;
import com.stage.onlinecourse.security.repository.MongoDbRepositoryImpl;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Transactional
@AllArgsConstructor @Data
public class MongoDocumentService {
	MongoDbRepository mongoDbRepository;
	
	public void testerz() {
		mongoDbRepository.mongoClient();
	}
}
