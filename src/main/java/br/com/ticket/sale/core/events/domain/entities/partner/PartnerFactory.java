package br.com.ticket.sale.core.events.domain.entities.partner;

import br.com.ticket.sale.core.common.domain.value_objects.Name;

public class PartnerFactory {

    public static Partner create(String name) {

        return new Partner(
                new PartnerConstructorProps(
                        new PartnerId(),
                        new Name(name)
                )
        );

    }
}
