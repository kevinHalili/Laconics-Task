package com.task.schoolservis.dto.userDto;

import com.task.schoolservis.dto.laptopDto.LaptopSaveDto;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveDto {
    @NotBlank(message = "Full Name is a required field")
    private String fullName;
    @NotBlank(message = "Email is a required field")
    private String email;
    @NotBlank(message = "Password is a required field")
    @Size(min = 6, message = "Password size must be greater than 5 characters")
    private String password;
    @Valid
    private LaptopSaveDto laptopSaveDto;
}
