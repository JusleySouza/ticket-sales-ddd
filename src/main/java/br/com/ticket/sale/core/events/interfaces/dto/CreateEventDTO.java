package br.com.ticket.sale.core.events.interfaces.dto;

import java.time.LocalDateTime;

public record CreateEventDTO(
    String name,
    String description,
    LocalDateTime date,
    String partnerId
) {}
