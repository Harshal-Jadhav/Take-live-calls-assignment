package com.playo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.playo.repositories.EventRepository;
import com.playo.repositories.RequestRepository;
import com.playo.repositories.UserRepository;

/**
 * 
 * @author harshal jadhav Email- harshaljadhav1000@gmail.com Phone - +91 8149392368
 *
 */

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = { EventRepository.class, UserRepository.class, RequestRepository.class })
public class PlayoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayoAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
