package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedHandler implements DomainEventHandler<OrderCreatedEvent> {

    @Override
    public void handle(OrderCreatedEvent event) {
        System.out.println("Order completed!");
    }
}
