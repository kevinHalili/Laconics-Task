package com.task.schoolservis.dto.ticketDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdateDto {
    private String description;
    @NotBlank(message = "Ticket must have a status OPEN/CLOSED")
    private String status;
}
