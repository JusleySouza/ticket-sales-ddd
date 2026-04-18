package br.com.ticket.sale.core.events.domain.repositories;

import br.com.ticket.sale.core.common.domain.repositories.IRepository;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends IRepository<Event, EventId> {

    void add(Event entity);

    Optional<Event> findById(EventId id);

    List<Event> findAll();

    void delete(EventId id);

}
