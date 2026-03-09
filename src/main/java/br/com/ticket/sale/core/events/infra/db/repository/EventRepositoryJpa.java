package br.com.ticket.sale.core.events.infra.db.repository;

import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.repositories.EventRepository;
import br.com.ticket.sale.core.events.infra.persistence.jpa.mappers.EventMapper;
import br.com.ticket.sale.core.events.infra.persistence.jpa.repositories.EventJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventRepositoryJpa implements EventRepository {

    private final EventJpaRepository repository;

    public EventRepositoryJpa(EventJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(Event entity) {
        repository.save(EventMapper.fromDomain(entity));
    }

    @Override
    public Optional<Event> findById(EventId id) {
        return repository
                .findById(id.getValue())
                .map(EventMapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return repository
                .findAll()
                .stream()
                .map(EventMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(EventId id) {
        repository.deleteById(id.getValue());
    }
}
