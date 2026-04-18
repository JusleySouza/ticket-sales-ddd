package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.PartnerCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class PartnerCreatedHandler implements DomainEventHandler<PartnerCreatedEvent> {
    public void handle(PartnerCreatedEvent event) {
        System.out.println("Partner created!");
    }
}
