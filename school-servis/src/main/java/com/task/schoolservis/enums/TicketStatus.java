package com.task.schoolservis.enums;

public enum TicketStatus {
    OPEN( "Open" ),
    CLOSED( "Closed" );

    public final String label;


    TicketStatus(String label) {
        this.label = label;
    }
}

