package br.com.ticket.sale.core.events.infra.event;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DomainEventInitializer {

    private final DomainEventManager manager;
    private final List<DomainEventHandler<?>> handlers;

    public DomainEventInitializer(DomainEventManager manager, List<DomainEventHandler<?>> handlers) {
        this.manager = manager;
        this.handlers = handlers;
    }

    @PostConstruct
    public void init() {
        for (DomainEventHandler<?> handler : handlers) {
            Class<?> eventType = resolveEventType(handler);
            manager.register((Class) eventType, handler);
        }
    }

    private Class<?> resolveEventType(DomainEventHandler<?> handler) {
        Class<?> targetClass = AopUtils.getTargetClass(handler);
        ResolvableType resolvableType = ResolvableType.forClass(targetClass)
                .as(DomainEventHandler.class);
        return resolvableType.getGeneric(0).resolve();
    }
}
