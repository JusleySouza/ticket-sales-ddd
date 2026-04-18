package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.PartnerChangedNameEvent;
import org.springframework.stereotype.Component;

@Component
public class PartnerChangedNameHandler implements DomainEventHandler<PartnerChangedNameEvent> {
    public void handle(PartnerChangedNameEvent event) {
        System.out.println("Partner changed name to: " + event.getNewName());
    }
}
