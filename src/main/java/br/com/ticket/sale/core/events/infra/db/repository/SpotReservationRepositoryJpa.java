package br.com.ticket.sale.core.events.infra.db.repository;

import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.domain.entities.reservation.SpotReservation;
import br.com.ticket.sale.core.events.domain.repositories.SpotReservationRepository;
import br.com.ticket.sale.core.events.infra.persistence.jpa.mappers.SpotReservationMapper;
import br.com.ticket.sale.core.events.infra.persistence.jpa.repositories.SpotReservationJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SpotReservationRepositoryJpa implements SpotReservationRepository {

    private final SpotReservationJpaRepository repository;

    public SpotReservationRepositoryJpa(SpotReservationJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(SpotReservation reservation) {
        repository.save(SpotReservationMapper.fromDomain(reservation));
    }

    @Override
    public Optional<SpotReservation> findById(EventSpotId id) {
        return repository
                .findById(id.getValue())
                .map(SpotReservationMapper::toDomain);
    }

    @Override
    public List<SpotReservation> findAll() {
        return repository
                .findAll()
                .stream()
                .map(SpotReservationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(EventSpotId id) {
        repository.deleteById(id.getValue());
    }
}
