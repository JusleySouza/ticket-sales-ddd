package br.com.ticket.sale.core.events.infra.persistence.jpa.mappers;

import br.com.ticket.sale.core.events.domain.entities.reservation.SpotReservation;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.SpotReservationEntity;

public class SpotReservationMapper {

    public static SpotReservationEntity fromDomain(SpotReservation reservation) {

        SpotReservationEntity entity = new SpotReservationEntity();

        entity.setSpotId(reservation.getSpotId().getValue());
        entity.setCustomerId(reservation.getCustomerId().getValue());
        entity.setReservationDate(reservation.getReservationDate());

        return entity;
    }

    public static SpotReservation toDomain(SpotReservationEntity entity) {

        return new SpotReservation(
                new EventSpotId(entity.getSpotId()),
                new CustomerId(entity.getCustomerId()),
                entity.getReservationDate()
        );
    }
}