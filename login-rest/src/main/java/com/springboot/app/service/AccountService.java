package com.springboot.app.service;

import java.util.List;

import com.springboot.app.entities.AppRole;
import com.springboot.app.entities.AppUser;

public interface AccountService {

	AppUser addNewUser(AppUser appUser);
	AppRole addNewRole(AppRole appRole);
	void addRoleToUser(String username,String roleName);
	AppUser loadUserByUsername(String username);
	List<AppUser> listUsers();
}
