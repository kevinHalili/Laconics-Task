package com.task.schoolservis.dto.role;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleSaveDto {
    @NotBlank(message = "Role name must not be blank")
    private String name;

    public void setName(String name) {
        this.name = name.toLowerCase( Locale.ROOT );
    }
}
