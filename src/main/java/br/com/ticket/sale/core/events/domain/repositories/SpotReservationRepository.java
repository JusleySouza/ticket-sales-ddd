package br.com.ticket.sale.core.events.domain.repositories;

import br.com.ticket.sale.core.common.domain.repositories.IRepository;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.domain.entities.reservation.SpotReservation;

import java.util.List;
import java.util.Optional;

public interface SpotReservationRepository extends IRepository<SpotReservation, EventSpotId> {

    void add(SpotReservation reservation);

    Optional<SpotReservation> findById(EventSpotId spotId);

    List<SpotReservation> findAll();

    void delete(EventSpotId spotId);
}
