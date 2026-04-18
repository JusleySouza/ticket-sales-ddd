package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.CustomerCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreatedHandler implements DomainEventHandler<CustomerCreatedEvent> {

    @Override
    public void handle(CustomerCreatedEvent event) {
        System.out.println("Customer created!");
    }
}
