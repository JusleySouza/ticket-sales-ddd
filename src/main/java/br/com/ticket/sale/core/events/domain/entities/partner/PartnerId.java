package br.com.ticket.sale.core.events.domain.entities.partner;

import br.com.ticket.sale.core.common.domain.value_objects.Uuid;

public class PartnerId extends Uuid {

    public PartnerId() {
        super();
    }

    public PartnerId(String value) {
        super(value);
    }
}
