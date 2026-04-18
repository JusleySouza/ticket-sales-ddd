package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.EventSectionAdded;
import org.springframework.stereotype.Component;

@Component
public class EventSectionAddedHandler implements DomainEventHandler<EventSectionAdded> {

    @Override
    public void handle(EventSectionAdded event) {
        System.out.println("Event section created: " + event.getSectionId().getValue());
    }
}

