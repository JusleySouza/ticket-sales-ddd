package br.com.ticket.sale.core.events.application.commands.spot_reservation;

import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;

public record CreateSpotReservationCommand(
        EventSpotId eventSpotId,
        CustomerId customerId
) {}
