package com.task.schoolservis.service.imp;


import com.task.schoolservis.dto.password.ForgotPasswordDto;
import com.task.schoolservis.dto.password.PasswordChangeDto;
import com.task.schoolservis.dto.userDto.UserDto;
import com.task.schoolservis.dto.userDto.UserSaveDto;
import com.task.schoolservis.dto.userDto.UserUpdateDto;
import com.task.schoolservis.entity.Laptop;
import com.task.schoolservis.entity.Role;
import com.task.schoolservis.entity.User;
import com.task.schoolservis.exception.AppException;
import com.task.schoolservis.exception.NotFoundException;
import com.task.schoolservis.exception.ValidationException;
import com.task.schoolservis.repository.LaptopRepository;
import com.task.schoolservis.repository.RoleRepository;
import com.task.schoolservis.repository.UserRepository;
import com.task.schoolservis.security.JwtTokenProvider;
import com.task.schoolservis.service.UserService;
import com.task.schoolservis.util.MappingTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MappingTool mapper;
    private final RoleRepository roleRepository;
    private final LaptopRepository laptopRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, MappingTool mapper, RoleRepository roleRepository, LaptopRepository laptopRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
        this.laptopRepository = laptopRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    protected Specification<User> searchUser(String searchKeyword, List<String> fields) {
        Specification<User> us = ((root, query, criteriaBuilder) -> null);
        for (String field : fields) {
            us.and( (root, query, criteriaBuilder) -> criteriaBuilder.like( root.get( field ), "%" + searchKeyword + "%" ) );
        }

        return us;
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable, String searchKeyword) {
        Specification<User> us = ((root, query, criteriaBuilder) -> null);

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            us = searchUser( searchKeyword, List.of( "fullName", "email" ) );
        }
        Page<User> userPage = userRepository.findAll( us, pageable );

        return mapper.map( userPage, UserDto.class );

    }

    @Override
    public UserDto createUser(UserSaveDto userSaveDto) {
        User user = mapper.map( userSaveDto, User.class );
        Laptop laptop = mapper.map( userSaveDto.getLaptopSaveDto(), Laptop.class );
        laptop.setOwner( user );
        user.setPassword( passwordEncoder.encode( userSaveDto.getPassword() ) );
        user.setLaptop( laptop );
        User saved = userRepository.save( user );
        return mapper.map( saved, UserDto.class );
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto, Long userId) {
        User user = this.userRepository.findById( userId ).orElseThrow( () -> new NotFoundException(
                "User", "Id",
                userId.toString() ) );
        mapper.map( userUpdateDto,user );
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto assignRole(Long userId, Long roleId) {
        User user = this.userRepository.findById( userId ).orElseThrow( () -> new NotFoundException(
                "User", "Id",
                userId.toString() ) );

        Role role = this.roleRepository.findById( roleId ).orElseThrow( () -> new NotFoundException(
                "Role", "Id",
                roleId.toString() ) );

        if (user.getRoles().stream().anyMatch( r -> r.getId().equals( roleId ) )) {
            throw new AppException( HttpStatus.BAD_REQUEST, "User already has this role!" );
        }

        user.getRoles().add( role );

        this.userRepository.save( user );

        return this.mapper.map( user, UserDto.class );
    }

    @Override
    public UserDto revokeRole(Long userId, Long roleId) {
        User user = userRepository.findById( userId ).orElseThrow( () -> new NotFoundException(
                "User", "Id",
                userId.toString() ) );

        Role role = roleRepository.findById( roleId ).orElseThrow( () -> new NotFoundException(
                "Role", "Id",
                roleId.toString() ) );

        if (user.getRoles().stream().noneMatch( r -> r.getId().equals( roleId ) )) {
            throw new AppException( HttpStatus.BAD_REQUEST, "User does not have the specified role !" );
        }

        user.getRoles().removeIf( r -> Objects.equals( r.getId(), role.getId() ) );

        userRepository.save( user );

        return mapper.map( user, UserDto.class );
    }

    public boolean changePassword(PasswordChangeDto passwordChangeDto)
    {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email  ).orElseThrow( () -> new NotFoundException(
                "User", "Email",
                email ) );

        user.setPassword( passwordEncoder.encode(passwordChangeDto.getNewPassword()) );
        userRepository.save( user );
        return true;
    }
    @Override
    public String generateTokenForUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User", "email", email));
        return jwtTokenProvider.generatePwToken(user.getEmail());
    }

    @Override
    public void resetPassword(ForgotPasswordDto passwordChangeDto) {

        HashMap<String, String> errors = new HashMap<>();

        if (!Objects.equals(passwordChangeDto.getNewPassword(), passwordChangeDto.getConfirmPassword())) {
            errors.put("confirmPassword","New and confirmation passwords do not match!");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        User user = userRepository.findByEmail(jwtTokenProvider.getEmailFromToken(passwordChangeDto.getToken())).orElseThrow();
        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        userRepository.save(user);

    }



}
