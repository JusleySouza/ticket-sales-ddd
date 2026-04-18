package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.EventNameChanged;
import org.springframework.stereotype.Component;

@Component
public class EventNameChangedHandler implements DomainEventHandler<EventNameChanged> {

    @Override
    public void handle(EventNameChanged event) {
        System.out.println("Event Name changed: " + event.getName().getValue());
    }
}
