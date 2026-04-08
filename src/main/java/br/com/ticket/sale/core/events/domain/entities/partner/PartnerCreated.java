package br.com.ticket.sale.core.events.domain.entities.partner;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.DomainEvent;

import java.time.Instant;

public class PartnerCreated implements DomainEvent {

    private final PartnerId aggregateId;
    private final Name name;
    private final Instant occurredOn;
    private final int eventVersion = 1;

    public PartnerCreated(PartnerId aggregateId, Name name) {
        this.aggregateId = aggregateId;
        this.name = name;
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

    public Name getName() {
        return name;
    }
}
