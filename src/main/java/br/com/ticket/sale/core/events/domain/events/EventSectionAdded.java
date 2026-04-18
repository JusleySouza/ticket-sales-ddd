package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;

public class EventSectionAdded extends AbstractDomainEvent {
    private final EventSectionId sectionId;

    public EventSectionAdded(EventId eventId, EventSectionId sectionId) {
        super(eventId, 1);
        this.sectionId = sectionId;
    }

    public EventSectionId getSectionId() {
        return sectionId;
    }
}
