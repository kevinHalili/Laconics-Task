package com.task.schoolservis.dto.partDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartUpdateDto {

    private String description;
    @Min(value = 0, message = "Price must be a positive number")
    private double price;
    @Min(value = 1, message = "Stock value must be  greater than 0")
    private int stock;
}
