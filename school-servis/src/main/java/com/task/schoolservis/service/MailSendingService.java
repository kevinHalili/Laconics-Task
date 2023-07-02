package com.task.schoolservis.service;



import com.task.schoolservis.dto.EmailResponseDto;

public interface MailSendingService {

    EmailResponseDto sendSimpleMail(String email);

}
