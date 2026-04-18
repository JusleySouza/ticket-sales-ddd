package br.com.ticket.sale.core.common.domain.event;

import java.time.Instant;

public interface DomainEvent {
    Object getAggregateId();
    Instant getOccurredOn();
    int getEventVersion();
}
