package br.com.ticket.sale.core.events.infra.persistence.jpa.repositories;

import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.SpotReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotReservationJpaRepository extends JpaRepository<SpotReservationEntity, String> {}
