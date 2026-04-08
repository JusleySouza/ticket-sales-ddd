package br.com.ticket.sale.core.events.infra.gateways;

import br.com.ticket.sale.core.events.application.gateways.PaymentGateway;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentGatewayImpl implements PaymentGateway {
    @Override
    public void pay(String token, BigDecimal amount) {
        //simulation
    }
}
