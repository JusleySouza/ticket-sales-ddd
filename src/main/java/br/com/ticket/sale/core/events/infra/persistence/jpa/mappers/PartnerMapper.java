package br.com.ticket.sale.core.events.infra.persistence.jpa.mappers;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.PartnerJpaEntity;

public class PartnerMapper {

    public static PartnerJpaEntity fromDomain(Partner partner) {
        PartnerJpaEntity entity = new PartnerJpaEntity();
               entity.setId(partner.getId().getValue());
               entity.setName(partner.getName().getValue());
        return entity;
    }

    public static Partner toDomain(PartnerJpaEntity entity) {
        PartnerConstructorProps props =
                new PartnerConstructorProps(
                        new PartnerId(entity.getId()),
                        new Name(entity.getName())
                );
        return new Partner(props);
    }
}
