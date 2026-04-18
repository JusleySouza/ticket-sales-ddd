package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.EventDescriptionChanged;
import org.springframework.stereotype.Component;

@Component
public class EventDescriptionChangedHandler implements DomainEventHandler<EventDescriptionChanged> {

    @Override
    public void handle(EventDescriptionChanged event) {
        System.out.println("Event Description changed: " + event.getDescription());
    }
}

