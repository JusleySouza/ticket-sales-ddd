package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;

public class CustomerCreatedEvent extends AbstractDomainEvent {

    public CustomerCreatedEvent(CustomerId customerId) {
        super(customerId, 1);
    }

    public CustomerId getCustomerId() {
        return (CustomerId) getAggregateId();
    }

}
