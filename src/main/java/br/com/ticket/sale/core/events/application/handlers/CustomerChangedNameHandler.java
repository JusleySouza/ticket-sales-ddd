package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.CustomerNameChangedEvent;
import org.springframework.stereotype.Component;

@Component
public class CustomerChangedNameHandler implements DomainEventHandler<CustomerNameChangedEvent> {
    public void handle(CustomerNameChangedEvent event) {
        System.out.println("Customer changed name to: " + event.getNewName());
    }
}
