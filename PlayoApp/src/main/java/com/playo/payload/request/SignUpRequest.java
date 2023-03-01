package com.playo.payload.request;

import com.playo.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	private String username;
	private String password;
	private String email;
	private Role role;
}
