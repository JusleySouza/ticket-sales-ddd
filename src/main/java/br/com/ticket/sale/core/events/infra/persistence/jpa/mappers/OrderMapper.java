package br.com.ticket.sale.core.events.infra.persistence.jpa.mappers;
import br.com.ticket.sale.core.events.domain.entities.order.Order;
import br.com.ticket.sale.core.events.domain.entities.order.OrderId;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.OrderEntity;

public class OrderMapper {

    public static OrderEntity fromDomain(Order order) {

        OrderEntity entity = new OrderEntity();

        entity.setId(order.getId().getValue());
        entity.setCustomerId(order.getCustomerId().getValue());
        entity.setEventSpotId(order.getEventSpotId().getValue());
        entity.setAmount(order.getAmount());
        entity.setStatus(order.getStatus());

        return entity;
    }

    public static Order toDomain(OrderEntity entity) {

        return new Order(
                new OrderId(entity.getId()),
                new CustomerId(entity.getCustomerId()),
                new EventSpotId(entity.getEventSpotId()),
                entity.getAmount(),
                entity.getStatus()
        );
    }
}
