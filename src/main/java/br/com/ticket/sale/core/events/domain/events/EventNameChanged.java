package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;

public class EventNameChanged extends AbstractDomainEvent {
    private final Name name;

    public EventNameChanged(EventId eventId, Name name) {
        super(eventId, 1);
        this.name = name;
    }

    public EventId getEventId() {
        return (EventId) getAggregateId();
    }

    public Name getName() {
        return name;
    }
}
