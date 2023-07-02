package com.task.schoolservis.dto.partDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartSaveDto {
    @NotBlank(message = "Name is a required field")
    private String name;
    private String description;
    @Min(value = 0, message = "Price must be a positive number")
    private double price;
    @Min(value = 1, message = "Stock value must be  greater than 0")
    private int stock;
    @NotBlank(message = "Manufacturer is a required field")
    private String manufacturer;
}
