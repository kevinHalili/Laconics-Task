package com.task.schoolservis.dto.ticketDto;

import com.task.schoolservis.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketSaveDto {
    private String description;
    @NotBlank(message = "Ticket must have a status OPEN/CLOSED")
    private TicketStatus status;
}
