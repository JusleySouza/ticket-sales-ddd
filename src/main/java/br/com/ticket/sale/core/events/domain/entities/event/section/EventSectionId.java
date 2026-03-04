package br.com.ticket.sale.core.events.domain.entities.event.section;

import br.com.ticket.sale.core.common.domain.value_objects.Uuid;

public class EventSectionId extends Uuid {

    public EventSectionId() {
        super();
    }

    public EventSectionId(String value) {
        super(value);
    }
}
