package br.com.ticket.sale.core.events.interfaces.dto;

import java.time.LocalDateTime;

public record EventResponseDTO(
        String id,
        String name,
        String description,
        LocalDateTime date,
        Boolean published,
        Integer totalSpots,
        Integer totalSpotsReserved,
        String partnerId
) {}
