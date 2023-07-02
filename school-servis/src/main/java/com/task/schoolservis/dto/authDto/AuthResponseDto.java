package com.task.schoolservis.dto.authDto;

import com.task.schoolservis.dto.TokenDto;
import com.task.schoolservis.dto.userDto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

    private UserDto userDto;
    private TokenDto tokenDto;
}
