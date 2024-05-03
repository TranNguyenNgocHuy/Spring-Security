package com.example.DemoSecurity;

import com.example.DemoSecurity.Enum.RoleName;
import com.example.DemoSecurity.entity.Role;
import com.example.DemoSecurity.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoSecurityApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoSecurityApplication.class, args);
	}

	@Bean
    CommandLineRunner createRole(RoleRepository roleRepository) {
        return args -> {
			Role r1 = new Role(RoleName.ADMIN);
			Role r2 = new Role(RoleName.MANAGER);
			Role r3 = new Role(RoleName.USER);

			if (!roleRepository.existsByRoleName(RoleName.ADMIN)) {
				roleRepository.save(new Role(RoleName.ADMIN));
			}

			if (!roleRepository.existsByRoleName(RoleName.MANAGER)) {
				roleRepository.save(new Role(RoleName.MANAGER));
			}

			if (!roleRepository.existsByRoleName(RoleName.USER)) {
				roleRepository.save(new Role(RoleName.USER));
			}
		};
	}
}
