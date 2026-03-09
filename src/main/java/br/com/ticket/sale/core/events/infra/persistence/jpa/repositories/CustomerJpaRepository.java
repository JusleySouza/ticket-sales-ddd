package br.com.ticket.sale.core.events.infra.persistence.jpa.repositories;

import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository
        extends JpaRepository<CustomerJpaEntity,String> {
}
