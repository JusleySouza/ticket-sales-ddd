package br.com.ticket.sale.core.events.domain.entities.order;

import br.com.ticket.sale.core.common.domain.value_objects.Uuid;

public class OrderId extends Uuid {

    public OrderId() {
        super();
    }

    public OrderId(String value) {
        super(value);
    }
}
