package br.com.ticket.sale.core.events.domain.entities;

import br.com.ticket.sale.core.common.domain.value_objects.Uuid;

public class CustomerId extends Uuid {

    public CustomerId() {
        super();
    }

    public CustomerId(String value) {
        super(value);
    }

}
