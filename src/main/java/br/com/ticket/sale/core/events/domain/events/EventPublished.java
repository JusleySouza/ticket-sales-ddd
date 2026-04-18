package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;

public class EventPublished extends AbstractDomainEvent {
    public EventPublished(EventId eventId) {
        super(eventId, 1);
    }

    public EventId getEventId() {
        return (EventId) getAggregateId();
    }
}
