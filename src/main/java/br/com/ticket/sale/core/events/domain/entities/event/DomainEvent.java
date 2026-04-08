package br.com.ticket.sale.core.events.domain.entities.event;

import br.com.ticket.sale.core.common.domain.value_objects.ValueObject;

import java.time.Instant;

public interface DomainEvent {
    ValueObject<?> getAggregateId();
    Instant getOccurredOn();
    int getEventVersion();
}
