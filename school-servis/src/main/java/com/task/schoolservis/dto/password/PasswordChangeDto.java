package com.task.schoolservis.dto.password;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswordChangeDto {

    private String currentPassword;
    @Size(min = 6, message = "user.validate.password.Size.Min.6")
    private String newPassword;
    @Size(min = 6, message = "user.validate.password.Size.Min.6")
    private String confirmPassword;
}
