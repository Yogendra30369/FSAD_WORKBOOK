package com.klu.config;

import com.klu.model.AppUser;
import com.klu.model.Role;
import com.klu.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("employee").isEmpty()) {
                AppUser employee = new AppUser();
                employee.setUsername("employee");
                employee.setPassword(passwordEncoder.encode("emp123"));
                employee.setRole(Role.EMPLOYEE);
                userRepository.save(employee);
            }
        };
    }
}
