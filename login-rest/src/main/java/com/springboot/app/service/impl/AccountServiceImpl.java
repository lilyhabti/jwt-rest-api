package com.springboot.app.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.entities.AppRole;
import com.springboot.app.entities.AppUser;
import com.springboot.app.repository.AppRoleRepository;
import com.springboot.app.repository.AppUserRepository;
import com.springboot.app.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
	private AppUserRepository appUserRepository;

	private AppRoleRepository appRoleRepository;
	
	private PasswordEncoder  passwordEncoder;
	
	public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository,PasswordEncoder  passwordEncoder) {
		super();
		this.appUserRepository = appUserRepository;
		this.appRoleRepository = appRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public AppUser addNewUser(AppUser appUser) {
		
		String pw=appUser.getPassword();
		appUser.setPassword(passwordEncoder.encode(pw));

		return appUserRepository.save(appUser);
	}

	@Override
	public AppRole addNewRole(AppRole appRole) {

		return appRoleRepository.save(appRole);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser appUser = appUserRepository.findByUsername(username);
		AppRole appRole = appRoleRepository.findByRoleName(roleName);
		appUser.getAppRoles().add(appRole);
	}

	@Override
	public AppUser loadUserByUsername(String username) {
		
		return appUserRepository.findByUsername(username);
	}

	@Override
	public List<AppUser> listUsers() {
		
		return appUserRepository.findAll();
	}

}
