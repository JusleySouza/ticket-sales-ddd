package br.com.ticket.sale.core.common.domain.event;

import java.time.Instant;

public abstract class AbstractDomainEvent implements DomainEvent {

    private final Object aggregateId;
    private final Instant occurredOn;
    private final int eventVersion;

    protected AbstractDomainEvent(Object aggregateId, int eventVersion) {
        this.aggregateId = aggregateId;
        this.occurredOn = Instant.now();
        this.eventVersion = eventVersion;
    }

    @Override
    public Object getAggregateId() {
        return aggregateId;
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }

    @Override
    public int getEventVersion() {
        return eventVersion;
    }
}
