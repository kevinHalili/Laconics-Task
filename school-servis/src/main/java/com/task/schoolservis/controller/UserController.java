package com.task.schoolservis.controller;

import com.task.schoolservis.dto.password.PasswordChangeDto;
import com.task.schoolservis.dto.userDto.UserDto;
import com.task.schoolservis.dto.userDto.UserSaveDto;
import com.task.schoolservis.dto.userDto.UserUpdateDto;
import com.task.schoolservis.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserSaveDto userSaveDto) {
        return new ResponseEntity<>( userService.createUser( userSaveDto ), HttpStatus.CREATED );
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable, String searchKeyword) {
        return new ResponseEntity<>( userService.getAllUsers( pageable, searchKeyword ), HttpStatus.OK );
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(UserUpdateDto userUpdateDto, @Valid @PathVariable Long userId) {
        return new ResponseEntity<>( userService.updateUser( userUpdateDto, userId ), HttpStatus.CREATED );
    }

    @PostMapping("/{userId}/role")
    public ResponseEntity<UserDto> assignRole(@PathVariable Long userId, Long roleId) {
        return new ResponseEntity<>( userService.assignRole( userId, roleId ), HttpStatus.CREATED );
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<UserDto> revokeRole(@PathVariable Long userId, Long roleId) {
        return new ResponseEntity<>( userService.revokeRole( userId, roleId ), HttpStatus.CREATED );
    }

    @PostMapping("/password-change")
    public ResponseEntity<Boolean> changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        return new ResponseEntity<>( userService.changePassword(  passwordChangeDto ), HttpStatus.CREATED );
    }


}
