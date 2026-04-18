package br.com.ticket.sale.core.events.domain.repositories;

import br.com.ticket.sale.core.common.domain.repositories.IRepository;
import br.com.ticket.sale.core.events.domain.entities.order.Order;
import br.com.ticket.sale.core.events.domain.entities.order.OrderId;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends IRepository<Order, OrderId> {

    void add(Order order);

    Optional<Order> findById(OrderId id);

    List<Order> findAll();

    void delete(OrderId id);
}
