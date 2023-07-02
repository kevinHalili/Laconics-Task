package com.task.schoolservis.dto.userDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.task.schoolservis.entity.Laptop;
import com.task.schoolservis.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private List<Role> roles;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Laptop laptop;
}
