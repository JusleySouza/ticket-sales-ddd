package br.com.ticket.sale.core.events.infra.event;

import br.com.ticket.sale.core.common.domain.entity.AggregateRoot;
import br.com.ticket.sale.core.common.domain.event.DomainEvent;
import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DomainEventManager {

    private final Map<Class<? extends DomainEvent>, List<DomainEventHandler<? extends DomainEvent>>> handlers = new HashMap<>();

    public <T extends DomainEvent> void register(Class<T> eventType, DomainEventHandler<T> handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    public void publish(AggregateRoot<?> aggregateRoot) {

        for (DomainEvent event : aggregateRoot.getEvents()) {

            List<DomainEventHandler<?>> eventHandlers = handlers.get(event.getClass());

            if (eventHandlers != null) {
                for (DomainEventHandler handler : eventHandlers) {
                    handler.handle(event);
                }
            }
        }
        aggregateRoot.clearEvents();
    }
}
