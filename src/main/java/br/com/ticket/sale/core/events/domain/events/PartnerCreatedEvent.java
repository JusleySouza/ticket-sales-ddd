package br.com.ticket.sale.core.events.domain.events;

import br.com.ticket.sale.core.common.domain.event.AbstractDomainEvent;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;

public class PartnerCreatedEvent extends AbstractDomainEvent {

    public PartnerCreatedEvent(PartnerId partnerId) {super(partnerId, 1);}

    public PartnerId getCustomerId() {
        return (PartnerId) getAggregateId();
    }
}
