package br.com.ticket.sale.core.events.domain.entities.event;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;

import java.time.LocalDateTime;
import java.util.Set;

public record EventConstructorProps(
        EventId id,
        Name name,
        String description,
        LocalDateTime date,
        boolean isPublished,
        int totalSpots,
        int totalSpotsReserved,
        PartnerId partnerId,
        Set<EventSection> sections
) {}
