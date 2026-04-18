package br.com.ticket.sale.core.common.domain.entity;

import br.com.ticket.sale.core.common.domain.event.DomainEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AggregateRoot<ID> extends Entity<ID> {

    private final Set<DomainEvent> events = new HashSet<>();

    public void addEvent(DomainEvent event) {
        this.events.add(event);
    }

    public void clearEvents() {
        this.events.clear();
    }

    public Set<DomainEvent> getEvents() {
        return Collections.unmodifiableSet(events);
    }

}
