package br.com.ticket.sale.core.events.domain.entities.event.spot;

import br.com.ticket.sale.core.common.domain.value_objects.Uuid;

public class EventSpotId extends Uuid {

    public EventSpotId() {
        super();
    }

    public EventSpotId(String value) {
        super(value);
    }
}
