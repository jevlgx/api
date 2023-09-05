package com.stage.onlinecourse.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stage.onlinecourse.security.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {

}
