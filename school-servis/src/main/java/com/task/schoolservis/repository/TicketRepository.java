package com.task.schoolservis.repository;

import com.task.schoolservis.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    @Query("select t from Ticket t where t.laptop.owner.id = :userId")
    Page<Ticket> findAllTicketsByLaptopOwner(Long userId, Pageable pageable);
}
