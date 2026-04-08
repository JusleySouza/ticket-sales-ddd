package br.com.ticket.sale.core.events.interfaces.dto;

import java.math.BigDecimal;

public record CreateEventSectionDTO(
        String name,
        String description,
        int totalSpots,
        BigDecimal price
) {}
