package com.task.schoolservis.controller;

import com.task.schoolservis.dto.EmailResponseDto;
import com.task.schoolservis.dto.TokenDto;
import com.task.schoolservis.dto.authDto.AuthDto;
import com.task.schoolservis.dto.authDto.AuthResponseDto;
import com.task.schoolservis.dto.password.ForgotPasswordDto;
import com.task.schoolservis.dto.userDto.UserDto;
import com.task.schoolservis.entity.User;
import com.task.schoolservis.exception.NotFoundException;
import com.task.schoolservis.repository.UserRepository;
import com.task.schoolservis.security.JwtTokenProvider;
import com.task.schoolservis.service.MailSendingService;
import com.task.schoolservis.service.UserService;
import com.task.schoolservis.util.MappingTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;


    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;
    private final MappingTool mapper;
    private final UserService userService;
    private final MailSendingService mailSendingService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, MappingTool mapper, UserService userService, MailSendingService mailSendingService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.mailSendingService = mailSendingService;
    }




    @PostMapping("/get-token")
    public ResponseEntity<AuthResponseDto> getToken(@RequestBody AuthDto userAuthDto) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken( userAuthDto.getEmail(), userAuthDto.getPassword() ) );

        SecurityContextHolder.getContext().setAuthentication( authentication );

        TokenDto tokenDto = jwtTokenProvider.generateToken( authentication );
        User user = userRepository.findByEmail( userAuthDto.getEmail() ).orElseThrow( () -> new NotFoundException(
                "User", "Email",
                userAuthDto.getEmail() ) );
        UserDto userDto = mapper.map( user, UserDto.class );
        AuthResponseDto userAuthResponseDto = new AuthResponseDto();
        userAuthResponseDto.setUserDto( userDto );
        userAuthResponseDto.setTokenDto( tokenDto );
        return new ResponseEntity<>(
                userAuthResponseDto,
                HttpStatus.OK
        );
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        TokenDto tokenDto = jwtTokenProvider.generateToken( authentication );

        return new ResponseEntity<>(
                tokenDto,
                HttpStatus.OK
        );
    }

    @PostMapping("/forgot-password/mail")
    public ResponseEntity<EmailResponseDto> forgotPassword(@RequestBody String email) {
        return new ResponseEntity<>(
                mailSendingService.sendSimpleMail(email),
                HttpStatus.OK
        );
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ForgotPasswordDto password) {
        userService.resetPassword(password);
        return ResponseEntity.ok().build();
    }



}
