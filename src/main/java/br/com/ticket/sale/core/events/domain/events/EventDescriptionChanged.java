package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;

public class EventDescriptionChanged extends AbstractDomainEvent {
    private final String description;

    public EventDescriptionChanged(EventId eventId, String description) {
        super(eventId, 1);
        this.description = description;
    }

    public EventId getEventId() {
        return (EventId) getAggregateId();
    }

    public String getDescription() {
        return description;
    }
}
