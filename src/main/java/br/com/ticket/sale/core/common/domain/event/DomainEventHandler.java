package br.com.ticket.sale.core.common.domain.event;

public interface DomainEventHandler<T extends DomainEvent> {
    void handle(T event);
}
