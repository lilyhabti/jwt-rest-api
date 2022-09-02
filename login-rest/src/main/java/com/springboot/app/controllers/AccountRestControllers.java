package com.springboot.app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.entities.AppRole;
import com.springboot.app.entities.AppUser;
import com.springboot.app.service.AccountService;

import lombok.Data;

@RestController
public class AccountRestControllers {
	
	private AccountService accountService;
	
	public AccountRestControllers(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping(path = "/users")
	public List<AppUser> getAllUsers(){
		
		return accountService.listUsers();
	}

	@PostMapping(path = "/users")
	public AppUser saveUser(@RequestBody AppUser appUser) {
		
		return accountService.addNewUser(appUser);
	}
	
	@PostMapping(path = "/roles")
	public AppRole saveRole(@RequestBody AppRole appRole) {
		
		return accountService.addNewRole(appRole);
	}
	
	@PostMapping(path = "/addRoleToUser")
	public void addRoleToUser(@RequestBody RoleUserForm roleUserForm ) {
		
		accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRoleName());
	}
}

@Data
class RoleUserForm {
	private String username;
	private String roleName;
}
