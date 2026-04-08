package br.com.ticket.sale.core.events.interfaces.dto;

import java.math.BigDecimal;

public record EventSectionResponseDTO(
        String id,
        String name,
        String description,
        Boolean isPublished,
        Integer totalSpots,
        int totalSpotsReserved,
        BigDecimal price
) {}
