package br.com.ticket.sale.core.events.infra.persistence.jpa.repositories;

import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.EventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository
        extends JpaRepository<EventJpaEntity,String> {
}
