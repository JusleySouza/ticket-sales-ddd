package br.com.ticket.sale.core.events.infra.persistence.jpa.repositories;

import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.PartnerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerJpaRepository
        extends JpaRepository<PartnerJpaEntity, String> {
}
