package com.task.schoolservis.dto.ticketDto;

import com.task.schoolservis.dto.laptopDto.LaptopDto;
import com.task.schoolservis.dto.partDto.PartDto;
import com.task.schoolservis.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long id;
    private LaptopDto laptop;
    private PartDto part;
    private String description;
    private TicketStatus status;
}
