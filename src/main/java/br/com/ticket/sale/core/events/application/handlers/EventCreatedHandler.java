package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.EventCreated;
import org.springframework.stereotype.Component;

@Component
public class EventCreatedHandler implements DomainEventHandler<EventCreated> {

    @Override
    public void handle(EventCreated event) {
        System.out.println("Event created for partner: " + event.getPartnerId().getValue());

        System.out.println("Event ID: " + event.getEventId().getValue());
    }
}
