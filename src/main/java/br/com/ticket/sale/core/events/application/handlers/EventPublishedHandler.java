package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.EventPublished;
import org.springframework.stereotype.Component;

@Component
public class EventPublishedHandler implements DomainEventHandler<EventPublished> {

    @Override
    public void handle(EventPublished event) {
        System.out.println("Event published: " + event.getEventId().getValue());
    }
}

