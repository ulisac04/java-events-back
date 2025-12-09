package com.isaacjava.demoapi.data;

import com.isaacjava.demoapi.domain.Role;
import com.isaacjava.demoapi.domain.User;
import com.isaacjava.demoapi.repository.RolRepository;
import com.isaacjava.demoapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role adminRole = rolRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_ADMIN");
                    return rolRepository.save(role);
                });

        Role userRole = rolRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_USER");
                    return rolRepository.save(role);
                });

        if (userRepository.findByUsername("admin").isEmpty()){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.getRoles().add(adminRole);
            userRepository.save(admin);

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(userRole);

            admin.setRoles(adminRoles);

            userRepository.save(admin);
            System.out.println("Admin user created: admin / admin123");
        }

        if (userRepository.findByUsername("admin").isEmpty()){
            User uss = new User();
            uss.setUsername("user");
            uss.setPassword(passwordEncoder.encode("uss123"));
            uss.getRoles().add(adminRole);
            userRepository.save(uss);

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            uss.setRoles(userRoles);

            userRepository.save(uss);
            System.out.println("user created: admin / admin123");
        }

    }
}
