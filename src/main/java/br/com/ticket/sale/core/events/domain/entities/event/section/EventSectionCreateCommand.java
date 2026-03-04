package br.com.ticket.sale.core.events.domain.entities.event.section;

import br.com.ticket.sale.core.common.domain.value_objects.Name;

import java.math.BigDecimal;

public record EventSectionCreateCommand(
        Name name,
        String description,
        int totalSpots,
        BigDecimal price
) {}
