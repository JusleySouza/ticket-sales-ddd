package br.com.ticket.sale.core.common.application;

import br.com.ticket.sale.core.common.domain.entity.AggregateRoot;
import br.com.ticket.sale.core.events.infra.event.DomainEventManager;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private final DomainEventManager eventManager;

    public ApplicationService(DomainEventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void commit(AggregateRoot<?> aggregate) {
        eventManager.publish(aggregate);
        aggregate.clearEvents();
    }
}
