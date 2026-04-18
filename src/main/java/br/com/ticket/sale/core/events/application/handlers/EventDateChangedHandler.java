package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.EventDateChanged;
import org.springframework.stereotype.Component;

@Component
public class EventDateChangedHandler implements DomainEventHandler<EventDateChanged> {

    @Override
    public void handle(EventDateChanged event) {
        System.out.println("Event Date changed: " + event.getDate());
    }
}

