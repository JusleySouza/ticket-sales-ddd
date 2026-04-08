package br.com.ticket.sale.core.events.domain.entities.event;

import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;

import java.time.Instant;

public class EventCreated implements DomainEvent {

    private final PartnerId aggregateId;
    private final EventId eventId;
    private final Instant occurredOn;
    private final int eventVersion = 1;

    public EventCreated(PartnerId aggregateId, EventId eventId) {
        this.aggregateId = aggregateId;
        this.eventId = eventId;
        this.occurredOn = Instant.now();
    }

    @Override
    public PartnerId getAggregateId() {
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

    public EventId getEventId() {
        return eventId;
    }
}
