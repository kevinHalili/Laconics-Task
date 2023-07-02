package com.task.schoolservis.service;

import com.task.schoolservis.dto.ticketDto.TicketDto;
import com.task.schoolservis.dto.ticketDto.TicketSaveDto;
import com.task.schoolservis.dto.ticketDto.TicketUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    TicketDto createTicket(Long laptopId, Long partId, TicketSaveDto ticketSaveDto);

    TicketDto updateTicket(Long ticketId, TicketUpdateDto ticketUpdateDto);

    void deleteTicket(Long ticketId);

    Page<TicketDto> getAllTicketsForStudent(Long userId, Pageable pageable);

}
