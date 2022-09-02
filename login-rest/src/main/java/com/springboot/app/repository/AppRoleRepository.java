package com.springboot.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

	AppRole findByRoleName(String roleName);
}
