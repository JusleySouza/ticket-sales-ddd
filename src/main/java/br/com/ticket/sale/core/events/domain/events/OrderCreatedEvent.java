package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;
import br.com.ticket.sale.core.events.domain.entities.order.OrderId;

public class OrderCreatedEvent extends AbstractDomainEvent {

    public OrderCreatedEvent(OrderId orderId) {
        super(orderId, 1);
    }

    public OrderId getOrderId() {
        return (OrderId) getAggregateId();
    }
}
