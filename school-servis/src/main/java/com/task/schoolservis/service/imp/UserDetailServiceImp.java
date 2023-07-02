package com.task.schoolservis.service.imp;

import com.task.schoolservis.entity.Role;
import com.task.schoolservis.entity.User;
import com.task.schoolservis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail( email )
                .orElseThrow( () -> new UsernameNotFoundException( "User not found with email : " + ": " + email ) );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), this.mapRolesToAuthorities( user.getRoles() ) );
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map( (role -> new SimpleGrantedAuthority( role.getName() )) ).collect( Collectors.toList() );
    }
}
