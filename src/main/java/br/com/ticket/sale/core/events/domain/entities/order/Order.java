package br.com.ticket.sale.core.events.domain.entities.order;

import br.com.ticket.sale.core.common.domain.entity.AggregateRoot;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.domain.events.OrderCreatedEvent;
import br.com.ticket.sale.core.events.domain.events.PaymentApprovedEvent;

import java.math.BigDecimal;
import java.util.Map;

// Representa uma ordem de compra para um evento, associada a um cliente e a um spot específico.
public class Order extends AggregateRoot<OrderId> {

    private OrderId id;
    private CustomerId customerId;
    private EventSpotId eventSpotId;
    private BigDecimal amount;
    private OrderStatus status;

    public Order(OrderId id,
                 CustomerId customerId,
                 EventSpotId eventSpotId,
                 BigDecimal amount,
                 OrderStatus status) {

        this.id = id == null ? new OrderId() : id;
        this.customerId = customerId;
        this.eventSpotId = eventSpotId;
        this.amount = amount;
        this.status = status == null ? OrderStatus.PENDING : status;
    }

    public static Order create(CustomerId customerId, EventSpotId spotId, BigDecimal amount) {
        Order order = new Order(new OrderId(), customerId, spotId, amount, OrderStatus.PENDING);
        order.addEvent(new OrderCreatedEvent(order.getId()));
        return order;
    }

    public void pay() {
        this.status = OrderStatus.PAID;
        this.addEvent(new PaymentApprovedEvent(this.getId()));
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    public OrderId getId() { return id; }

    public CustomerId getCustomerId() { return customerId; }

    public EventSpotId getEventSpotId() { return eventSpotId; }

    public BigDecimal getAmount() { return amount; }

    public OrderStatus getStatus() { return status; }

    @Override
    public Map<String, Object> toJSON() {
        return Map.of(
                "id", id.getValue(),
                "customerId", customerId.getValue(),
                "eventSpotId", eventSpotId,
                "amount", amount,
                "status", status
        );
    }
}
