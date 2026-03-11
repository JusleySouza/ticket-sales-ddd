package br.com.ticket.sale.core.events.infra.db.repository;

import br.com.ticket.sale.core.events.domain.entities.order.Order;
import br.com.ticket.sale.core.events.domain.entities.order.OrderId;
import br.com.ticket.sale.core.events.domain.repositories.OrderRepository;
import br.com.ticket.sale.core.events.infra.persistence.jpa.mappers.OrderMapper;
import br.com.ticket.sale.core.events.infra.persistence.jpa.repositories.OrderJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryJpa implements OrderRepository {

    private final OrderJpaRepository repository;

    public OrderRepositoryJpa(OrderJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(Order order) {
        repository.save(OrderMapper.fromDomain(order));
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return repository
                .findById(id.getValue())
                .map(OrderMapper::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return repository
                .findAll()
                .stream()
                .map(OrderMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(OrderId id) {
        repository.deleteById(id.getValue());
    }
}
