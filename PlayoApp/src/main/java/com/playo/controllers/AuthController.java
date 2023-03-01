package com.playo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playo.models.User;
import com.playo.payload.request.LoginRequest;
import com.playo.payload.response.MessageResponse;
import com.playo.payload.response.UserResponse;
import com.playo.security.jwt.JwtHelper;

/*
 * This is a Authentication Controller to Handle the login and logout requests.
 * The endpoints for this controllers are as follows.
 * 
 * Login ----> http://localhost:8088/playo/authenticate/login. (This takes loginRequest as a requestBody )
 * 
 * Logout ---> http://localhost:8088/playo/authenticate/logout.
 */

@RestController
@RequestMapping("/playo/authenticate")
public class AuthController {

	private JwtHelper helper;
	private AuthenticationManager authenticationManager;
	private ModelMapper modelMapper;

	// Constructor Dependency Injection.
	public AuthController(JwtHelper helper, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
		this.helper = helper;
		this.authenticationManager = authenticationManager;
		this.modelMapper = modelMapper;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authentivateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		ResponseCookie cookie = helper.generateCookie(user);
		UserResponse userResponse = modelMapper.map(user, UserResponse.class);

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(userResponse);
	}

	@GetMapping("/logout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = helper.deleteCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You have been Loged Out"));
	}
}
