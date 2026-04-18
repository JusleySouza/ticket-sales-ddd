package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;

public class PaymentApprovedEvent extends AbstractDomainEvent {

    public PaymentApprovedEvent(Object id) {
        super(id, 1);
    }

}
