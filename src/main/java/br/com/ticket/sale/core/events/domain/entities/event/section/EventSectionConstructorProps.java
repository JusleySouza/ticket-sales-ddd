package br.com.ticket.sale.core.events.domain.entities.event.section;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpot;

import java.math.BigDecimal;
import java.util.Set;

public record EventSectionConstructorProps(
        EventSectionId id,
        Name name,
        String description,
        boolean isPublished,
        int totalSpots,
        int totalSpotsReserved,
        BigDecimal price,
        Set<EventSpot> spots
) {}
