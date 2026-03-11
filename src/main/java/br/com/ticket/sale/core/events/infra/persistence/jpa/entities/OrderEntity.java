package br.com.ticket.sale.core.events.infra.persistence.jpa.entities;

import br.com.ticket.sale.core.events.domain.entities.order.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "customer_id", length = 50, nullable = false)
    private String customerId;

    @Column(name = "event_spot_id", length = 50, nullable = false)
    private String eventSpotId;

    @Column(name = "amount", length = 50, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private OrderStatus status;

    public OrderEntity() {}

    public OrderEntity(String id, String customerId, String eventSpotId, BigDecimal amount, OrderStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.eventSpotId = eventSpotId;
        this.amount = amount;
        this.status = status;
    }

    public String getId() { return id; }

    public String getCustomerId() { return customerId; }

    public String getEventSpotId() { return eventSpotId; }

    public BigDecimal getAmount() { return amount; }

    public OrderStatus getStatus() { return status; }

    public void setId(String id) { this.id = id; }

    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public void setEventSpotId(String eventSpotId) { this.eventSpotId = eventSpotId; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public void setStatus(OrderStatus status) { this.status = status; }

}
