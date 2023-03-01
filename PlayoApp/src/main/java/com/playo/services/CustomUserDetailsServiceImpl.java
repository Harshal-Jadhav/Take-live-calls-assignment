package com.playo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.playo.models.User;
import com.playo.repositories.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	// Constructor Dependency Injection.
	public CustomUserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not Found with username " + username));
		return user;
	}

}
