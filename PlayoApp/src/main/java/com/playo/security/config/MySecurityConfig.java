package com.playo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.playo.security.jwt.JwtAuthenticationFilter;
import com.playo.security.jwt.JwtEntryPoint;
import com.playo.services.CustomUserDetailsServiceImpl;

@Configuration
public class MySecurityConfig {

	@Autowired
	private JwtEntryPoint entryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private CustomUserDetailsServiceImpl userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	private final String[] AUTH_PERMIT_ALL = { "/swagger-resources/**", "/v3/api-docs/**", "/swagger-ui/**",
			"/playo/authenticate/**", "/playo/users/**", "/playo/events/**" };

	// We can add specific authorizations here.
	private final String[] AUTH_ADMIN = {};
	private final String[] AUTH_PLAYER = {};

	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(entryPoint).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeHttpRequests()
		.antMatchers(AUTH_ADMIN).hasAuthority("ADMIN")
		.antMatchers(AUTH_PLAYER).hasAuthority("PLAYER")
		.antMatchers(AUTH_PERMIT_ALL).permitAll()
		.anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
