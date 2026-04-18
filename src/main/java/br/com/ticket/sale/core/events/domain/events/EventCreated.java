package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;

public class EventCreated extends AbstractDomainEvent {
    private final EventId eventId;

    public EventCreated(PartnerId partnerId, EventId eventId) {
        super(partnerId, 1);
        this.eventId = eventId;
    }

    public PartnerId getPartnerId() { return (PartnerId) getAggregateId(); }

    public EventId getEventId() { return eventId; }
}
