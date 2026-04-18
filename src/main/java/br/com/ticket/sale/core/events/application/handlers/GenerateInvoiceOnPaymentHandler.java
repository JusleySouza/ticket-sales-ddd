package br.com.ticket.sale.core.events.application.handlers;

import br.com.ticket.sale.core.common.domain.event.DomainEventHandler;
import br.com.ticket.sale.core.events.domain.events.PaymentApprovedEvent;
import org.springframework.stereotype.Component;

@Component
public class GenerateInvoiceOnPaymentHandler implements DomainEventHandler<PaymentApprovedEvent> {

    @Override
    public void handle(PaymentApprovedEvent event) {
        System.out.println("Generating invoice for payment!");
    }
}
