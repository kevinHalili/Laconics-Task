package com.task.schoolservis;

import com.task.schoolservis.entity.Role;
import com.task.schoolservis.entity.User;
import com.task.schoolservis.repository.RoleRepository;
import com.task.schoolservis.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Optional;

@SpringBootApplication
@EnableWebMvc
public class SchoolServisApplication {

    public static void main(String[] args) {
        SpringApplication.run( SchoolServisApplication.class, args );
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {
            User admin = new User();
            admin.setFullName( "Kevin Halili" );
            admin.setEmail( "kevin_halili@outlook.com" );
            admin.setPassword( passwordEncoder.encode( "123456" ) );

            HashMap<String, User> hashMapOfUsers = new HashMap<>();
            hashMapOfUsers.put( "ROLE_ADMIN", admin );

            hashMapOfUsers.forEach( (roleToAdd, userToAdd) -> {
                User newUser;
                Optional<User> user = userRepository.findByEmail( userToAdd.getEmail() );
                if (user.isEmpty()) {
                    newUser = userToAdd;
                    userRepository.save( newUser );
                } else {
                    newUser = user.get();
                }

                Optional<Role> role = roleRepository.findByName( roleToAdd );
                Role newRole;
                if (role.isEmpty()) {
                    newRole = new Role();
                    newRole.setName( roleToAdd );
                    roleRepository.save( newRole );
                } else {
                    newRole = role.get();
                }

                if (newUser.getRoles().stream().noneMatch( r -> r.getId().equals( newRole.getId() ) )) {

                    newUser.getRoles().add( newRole );

                    userRepository.save( newUser );
                }
            } );
        };
    }
}
