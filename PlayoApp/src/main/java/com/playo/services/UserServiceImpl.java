package com.playo.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.playo.exceptions.UserNotFoundException;
import com.playo.models.User;
import com.playo.payload.request.SignUpRequest;
import com.playo.payload.response.UserResponse;
import com.playo.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private ModelMapper modelMapper;

	// Constructor Dependency Injection.
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserResponse createUser(SignUpRequest signUpRequest) {

		User user = modelMapper.map(signUpRequest, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return modelMapper.map(userRepository.save(user), UserResponse.class);
	}

	@Override
	public UserResponse findByUsername(String username) throws UserNotFoundException {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found with the username:" + username));
		return modelMapper.map(user, UserResponse.class);
	}

}
