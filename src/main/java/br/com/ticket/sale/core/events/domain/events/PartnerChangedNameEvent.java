package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;

public class PartnerChangedNameEvent extends AbstractDomainEvent {

    private final Name newName;

    public PartnerChangedNameEvent(PartnerId id, Name newName) {
        super(id, 1);
        this.newName = newName;
    }

    public PartnerId getPartnerId() {
        return (PartnerId) getAggregateId();
    }

    public Name getNewName() {
        return newName;
    }
}
