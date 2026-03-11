package br.com.ticket.sale.core.events.infra.persistence.jpa.repositories;

import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository  extends JpaRepository<OrderEntity, String> {}
