package com.springboot.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	AppUser findByUsername(String username);
}
