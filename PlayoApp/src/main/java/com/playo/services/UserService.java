package com.playo.services;

import com.playo.exceptions.UserNotFoundException;
import com.playo.payload.request.SignUpRequest;
import com.playo.payload.response.UserResponse;

public interface UserService {

	public UserResponse createUser(SignUpRequest signUpRequest);

	public UserResponse findByUsername(String username) throws UserNotFoundException;


}
