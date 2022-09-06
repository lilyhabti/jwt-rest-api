package com.springboot.app;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.app.entities.AppRole;
import com.springboot.app.entities.AppUser;
import com.springboot.app.service.AccountService;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class LoginRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginRestApplication.class, args);
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner start(AccountService accountService) {
		
		return args -> {
			accountService.addNewRole(new AppRole(null,"ADMIN"));
			accountService.addNewRole(new AppRole(null,"USER"));
			accountService.addNewRole(new AppRole(null,"COSTUMOR_MANAGER"));
			accountService.addNewRole(new AppRole(null,"PRODUCT_MANAGER"));
			accountService.addNewRole(new AppRole(null,"BILLS_MANAGER"));
			
			accountService.addNewUser(new AppUser(null,"Lily","12345",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"Red","12345",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"Sunshine","12345",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"Princess","12345",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"Ryle","12345",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"Joshy","12345",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"Alex","12345",new ArrayList<>()));
			
			accountService.addRoleToUser("Lily", "ADMIN");
			accountService.addRoleToUser("Lily", "USER");
			accountService.addRoleToUser("Red", "ADMIN");
			accountService.addRoleToUser("Sunshine", "USER");
			accountService.addRoleToUser("Princess", "COSTUMOR_MANAGER");
			accountService.addRoleToUser("Ryle", "PRODUCT_MANAGER");
			accountService.addRoleToUser("Joshy", "ADMIN");
			accountService.addRoleToUser("Alex", "BILLS_MANAGER");
			accountService.addRoleToUser("Alex", "PRODUCT_MANAGER");
		};
	}

}
