package com.task.schoolservis.dto.laptopDto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaptopSaveDto {
    @NotBlank(message = "Manufacturer is a required field")
    private String manufacturer;
    private String owner;
}
