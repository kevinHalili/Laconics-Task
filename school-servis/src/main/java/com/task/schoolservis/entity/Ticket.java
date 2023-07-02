package com.task.schoolservis.entity;

import com.task.schoolservis.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Laptop laptop;
    @ManyToOne
    private Part part;
    @Lob
    private String description;
    private TicketStatus status;
}
