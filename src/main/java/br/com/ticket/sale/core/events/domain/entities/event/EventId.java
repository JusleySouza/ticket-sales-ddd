package br.com.ticket.sale.core.events.domain.entities.event;

import br.com.ticket.sale.core.common.domain.value_objects.Uuid;

public class EventId extends Uuid {

    public EventId() {
        super();
    }

    public EventId(String value) {
        super(value);
    }
}
