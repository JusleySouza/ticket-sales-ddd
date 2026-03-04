package br.com.ticket.sale.core.events.domain.entities.event;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;

import java.time.LocalDateTime;

public record CreateEventCommand(
        Name name,
        String description,
        LocalDateTime date,
        PartnerId partnerId
) {}
