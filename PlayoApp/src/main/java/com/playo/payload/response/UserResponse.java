package com.playo.payload.response;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.playo.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private Long userId;
	private String username;
	private String email;
	@Enumerated(EnumType.STRING)
	private Role role;

}
