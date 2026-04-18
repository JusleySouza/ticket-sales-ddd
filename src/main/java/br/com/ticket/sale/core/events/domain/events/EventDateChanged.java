package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;

import java.time.LocalDateTime;

public class EventDateChanged extends AbstractDomainEvent {
    private final LocalDateTime date;

    public EventDateChanged(EventId eventId, LocalDateTime date) {
        super(eventId, 1);
        this.date = date;
    }

    public EventId getEventId() {
        return (EventId) getAggregateId();
    }

    public LocalDateTime getDate() {
        return date;
    }
}
