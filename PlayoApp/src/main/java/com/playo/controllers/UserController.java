package com.playo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playo.payload.request.SignUpRequest;
import com.playo.payload.response.UserResponse;
import com.playo.services.UserService;

/*
 * This is a UserController to handle all user related requests like 
 * 
 * -- Create new User - ((http://localhost:8088/playo/users/register)
 * 
 * we can add many more endpoints as required.
 */

@RestController
@RequestMapping("/playo/users")
public class UserController {

	private UserService userService;
	// Constructor Dependency Injection.
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerNewUser(@RequestBody SignUpRequest signUpRequest) {
		UserResponse user = userService.createUser(signUpRequest);

		return new ResponseEntity<UserResponse>(user, HttpStatus.CREATED);

	}

}
