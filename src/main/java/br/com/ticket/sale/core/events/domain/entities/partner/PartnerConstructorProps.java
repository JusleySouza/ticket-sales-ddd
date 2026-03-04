package br.com.ticket.sale.core.events.domain.entities.partner;

import br.com.ticket.sale.core.common.domain.value_objects.Name;

public record PartnerConstructorProps(
        PartnerId id,
        Name name
) {}
