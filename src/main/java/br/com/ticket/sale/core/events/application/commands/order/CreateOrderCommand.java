package br.com.ticket.sale.core.events.application.commands.order;

import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;

public record CreateOrderCommand(
        EventId eventId,
        EventSectionId eventSectionId,
        EventSpotId eventSpotId,
        CustomerId customerId,
        String cardToken
) {}
