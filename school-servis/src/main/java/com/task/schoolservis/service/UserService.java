package com.task.schoolservis.service;

import com.task.schoolservis.dto.password.ForgotPasswordDto;
import com.task.schoolservis.dto.password.PasswordChangeDto;
import com.task.schoolservis.dto.userDto.UserDto;
import com.task.schoolservis.dto.userDto.UserSaveDto;
import com.task.schoolservis.dto.userDto.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> getAllUsers(Pageable pageable, String searchKeyword);

    UserDto createUser(UserSaveDto userSaveDto);

    UserDto updateUser(UserUpdateDto userUpdateDto, Long userId);

    UserDto assignRole(Long userId, Long roleId);

    UserDto revokeRole(Long userId, Long roleId);
    boolean changePassword(PasswordChangeDto passwordChangeDto);
    String generateTokenForUser(String email);
    void resetPassword(ForgotPasswordDto passwordChangeDto);


}
