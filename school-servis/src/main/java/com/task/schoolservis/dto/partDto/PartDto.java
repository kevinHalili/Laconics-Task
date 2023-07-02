package com.task.schoolservis.dto.partDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String manufacturer;
}
