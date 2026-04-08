package br.com.ticket.sale.core.events.interfaces.dto;

import java.time.LocalDateTime;

public record UpdateEventDTO(
    String name,
    String description,
    LocalDateTime date
){}
