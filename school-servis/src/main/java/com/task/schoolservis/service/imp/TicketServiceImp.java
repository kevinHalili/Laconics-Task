package com.task.schoolservis.service.imp;

import com.task.schoolservis.dto.ticketDto.TicketDto;
import com.task.schoolservis.dto.ticketDto.TicketSaveDto;
import com.task.schoolservis.dto.ticketDto.TicketUpdateDto;
import com.task.schoolservis.entity.Laptop;
import com.task.schoolservis.entity.Part;
import com.task.schoolservis.entity.Ticket;
import com.task.schoolservis.entity.User;
import com.task.schoolservis.exception.AppException;
import com.task.schoolservis.exception.NotFoundException;
import com.task.schoolservis.repository.LaptopRepository;
import com.task.schoolservis.repository.PartRepository;
import com.task.schoolservis.repository.TicketRepository;
import com.task.schoolservis.repository.UserRepository;
import com.task.schoolservis.service.TicketService;
import com.task.schoolservis.util.MappingTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TicketServiceImp implements TicketService {

    private final TicketRepository ticketRepository;
    private final MappingTool mappingTool;
    private final UserRepository userRepository;
    private final PartRepository partRepository;
    private final LaptopRepository laptopRepository;

    public TicketServiceImp(TicketRepository ticketRepository, MappingTool mappingTool, UserRepository userRepository, PartRepository partRepository, LaptopRepository laptopRepository) {
        this.ticketRepository = ticketRepository;
        this.mappingTool = mappingTool;
        this.userRepository = userRepository;
        this.partRepository = partRepository;
        this.laptopRepository = laptopRepository;
    }


    protected Specification<Ticket> searchTicket(String searchKeyword, List<String> fields) {
        Specification<Ticket> ts = ((root, query, criteriaBuilder) -> null);
        for (String field : fields) {
            ts.and( (root, query, criteriaBuilder) -> criteriaBuilder.like( root.get( field ), "%" + searchKeyword + "%" ) );
        }

        return ts;
    }

    @Override
    public TicketDto createTicket(Long laptopId, Long partId, TicketSaveDto ticketSaveDto) {
        Part part = partRepository.findById( partId ).orElseThrow( () -> new NotFoundException( "Part", "Id", partId.toString() ) );
        Laptop laptop = laptopRepository.findById( laptopId ).orElseThrow( () -> new NotFoundException( "Laptop", "Id", laptopId.toString() ) );
        if (!Objects.equals( part.getManufacturer(), laptop.getManufacturer() )) {
            throw new AppException( HttpStatus.UNPROCESSABLE_ENTITY, "This part is not compatible for the specified laptop!" );
        } else if (part.getStock() == 0) {
            throw new AppException( HttpStatus.NOT_FOUND, "This part is out of stock!" );
        } else {
            Ticket ticket = mappingTool.map( ticketSaveDto, Ticket.class );
            ticket.setPart( part );
            ticket.setLaptop( laptop );
            part.setStock( part.getStock() - 1 );
            partRepository.save( part );
            Ticket saved = ticketRepository.save( ticket );
            return mappingTool.map( saved, TicketDto.class );
        }

    }

    @Override
    public TicketDto updateTicket(Long ticketId, TicketUpdateDto ticketUpdateDto) {
        Ticket ticket = ticketRepository.findById( ticketId ).orElseThrow( () -> new NotFoundException( "Ticket", "Id", ticketId.toString() ) );
        mappingTool.map( ticketUpdateDto, ticket );
        Ticket updated = ticketRepository.save( ticket );

        return mappingTool.map( updated, TicketDto.class );
    }

    @Override
    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById( ticketId ).orElseThrow( () -> new NotFoundException( "Ticket", "Id", ticketId.toString() ) );
        ticketRepository.delete( ticket );
    }

    @Override
    public Page<TicketDto> getAllTicketsForStudent(Long userId, Pageable pageable) {
        User user = userRepository.findById( userId ).orElseThrow( () -> new NotFoundException( "User", "Id", userId.toString() ) );
        Page<Ticket> ticketPage = ticketRepository.findAllTicketsByLaptopOwner( user.getId(), pageable );

        return mappingTool.map( ticketPage, TicketDto.class );
    }
}
