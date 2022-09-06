package com.springboot.app.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.app.JWTUtil;
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
	@PostAuthorize("hasAuthority('USER')")
	public List<AppUser> getAllUsers(){
		
		return accountService.listUsers();
	}

	@PostMapping(path = "/users")
	@PostAuthorize("hasAuthority('ADMIN')")
	public AppUser saveUser(@RequestBody AppUser appUser) {
		
		return accountService.addNewUser(appUser);
	}
	
	@PostMapping(path = "/roles")
	@PostAuthorize("hasAuthority('ADMIN')")
	public AppRole saveRole(@RequestBody AppRole appRole) {
		
		return accountService.addNewRole(appRole);
	}
	
	@PostMapping(path = "/addRoleToUser")
	@PostAuthorize("hasAuthority('ADMIN')")
	public void addRoleToUser(@RequestBody RoleUserForm roleUserForm ) {
		
		accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRoleName());
	}
	
	@GetMapping(path = "/refreshToken")
	public void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String authToken = request.getHeader(JWTUtil.HEADER);
		if(authToken!=null && authToken.startsWith(JWTUtil.PREFIX)) {
			
			try {
				
				String refreshToken = authToken.substring(JWTUtil.PREFIX.length());
				Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
				String username = decodedJWT.getSubject();
				AppUser appUser = accountService.loadUserByUsername(username);
				String jwtAccessToken = JWT.create()
						.withSubject(appUser.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_ACCESS_TOKEN))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles", appUser.getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
						.sign(algorithm);
				 
				Map<String,String> idToken = new HashMap<>();
				idToken.put("access-token", jwtAccessToken);
				idToken.put("refresh-token", refreshToken);
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), idToken);
				
			} catch (Exception e) {
//				response.setHeader("error-message", e.getMessage());
//				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				throw e;
			}
		}else {
			throw new RuntimeException("Refresh Token required!!");
		}
	}
	
//	@GetMapping(path ="/profil")
//	public AppUser profile(Principal principal) {
//		return accountService.loadUserByUsername(principal.getName());
//	}
}

@Data
class RoleUserForm {
	private String username;
	private String roleName;
}
