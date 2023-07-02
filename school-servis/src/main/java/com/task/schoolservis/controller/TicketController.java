package com.task.schoolservis.controller;

import com.task.schoolservis.dto.ticketDto.TicketDto;
import com.task.schoolservis.dto.ticketDto.TicketSaveDto;
import com.task.schoolservis.dto.ticketDto.TicketUpdateDto;
import com.task.schoolservis.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/laptop/{laptopId}/ticket")
    public ResponseEntity<TicketDto> createTicket(@PathVariable Long laptopId, Long partId, @Valid @RequestBody TicketSaveDto ticketSaveDto) {
        return new ResponseEntity<>( ticketService.createTicket( laptopId, partId, ticketSaveDto ), HttpStatus.CREATED );
    }


    @GetMapping("/user/{userId}/tickets")
    public ResponseEntity<Page<TicketDto>> getAllTicketsForStudent(@PathVariable Long userId, Pageable pageable) {
        return new ResponseEntity<>( ticketService.getAllTicketsForStudent( userId, pageable ), HttpStatus.OK );
    }


    @PutMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable Long ticketId, TicketUpdateDto ticketUpdateDto) {
        return new ResponseEntity<>( ticketService.updateTicket( ticketId, ticketUpdateDto ), HttpStatus.CREATED );
    }

    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket( ticketId );
        return ResponseEntity.ok().build();
    }

}
