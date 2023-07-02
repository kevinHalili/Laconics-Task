package com.task.schoolservis.dto.password;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class ForgotPasswordDto {

    private String token;
    @Size(min = 6, message = "Password size must not be less than 6 characters")
    private String newPassword;
    @Size(min = 6, message = "Password size must not be less than 6 characters")
    private String confirmPassword;


}
