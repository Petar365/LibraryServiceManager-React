package com.example.libraryservicemanager;

import com.example.libraryservicemanager.domain.RequestContext;
import com.example.libraryservicemanager.model.RoleEntity;
import com.example.libraryservicemanager.model.enumeration.Authority;
import com.example.libraryservicemanager.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LibraryServiceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceManagerApplication.class, args);
	}

	// Rerun uncommented to create roles
//	@Bean
//	CommandLineRunner commandLineRunner(RoleRepository roleRepository){
//		return args -> {
//			RequestContext.setUserId(0L);
//			var userRole = new RoleEntity();
//			userRole.setName(Authority.USER.name());
//			userRole.setAuthorities(Authority.USER);
//			roleRepository.save(userRole);
//
//			var adminRole = new RoleEntity();
//			adminRole.setName(Authority.ADMIN.name());
//			adminRole.setAuthorities(Authority.ADMIN);
//			roleRepository.save(adminRole);
//
//			RequestContext.start();
//		};
//	}
}
