package br.com.ticket.sale.core.events.infra.persistence.jpa.mappers;

import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpot;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.EventSpotJpaEntity;

public class EventSpotMapper {

    public static EventSpotJpaEntity fromDomain(EventSpot spot) {

        EventSpotJpaEntity entity = new EventSpotJpaEntity();

        entity.setId(spot.getId().getValue());
        entity.setLocation(spot.getLocation());
        entity.setReserved(spot.isReserved());
        entity.setPublished(spot.isPublished());

        return entity;
    }

    public static EventSpot toDomain(EventSpotJpaEntity entity) {

        EventSpotConstructorProps props =
                new EventSpotConstructorProps(
                        new EventSpotId(entity.getId()),
                        entity.getLocation(),
                        entity.isReserved(),
                        entity.isPublished()
                );

        return new EventSpot(props);
    }
}
