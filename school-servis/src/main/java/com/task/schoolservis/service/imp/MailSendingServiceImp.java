package com.task.schoolservis.service.imp;


import com.task.schoolservis.dto.EmailResponseDto;
import com.task.schoolservis.entity.User;
import com.task.schoolservis.exception.NotFoundException;
import com.task.schoolservis.repository.UserRepository;
import com.task.schoolservis.service.MailSendingService;
import com.task.schoolservis.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Service
public class MailSendingServiceImp implements MailSendingService {

    private final UserService userService;

    public MailSendingServiceImp(UserService userService, JavaMailSender javaMailSender,
                                 UserRepository userRepository) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    @Value("${spring.mail.username}")
    String sender;

    @Value("${app.forgot-password-url}")
    String baseUrl;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Override
    public EmailResponseDto sendSimpleMail(String email) {

        User user = userRepository.findByEmail( email ).orElseThrow( () -> new NotFoundException( "User", "email", email ) );
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper( message );
            helper.setFrom( sender );
            helper.setSubject( "Password reset" );
            String token = userService.generateTokenForUser( user.getEmail() );
            helper.setText( "Hi " + user.getFullName() + "!\n" + "A request has been made to reset your password." +
                    "Below you can find the reset password link. Please follow the link below.\n" + baseUrl + token );
            helper.setTo( user.getEmail() );
            javaMailSender.send( message );
            return new EmailResponseDto( "Email sent successfully" );
        } catch (Exception e) {
            return new EmailResponseDto( "Email failed to send" );
        }


    }


}
