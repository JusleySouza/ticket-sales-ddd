package br.com.ticket.sale.core.events.application.gateways;

import java.math.BigDecimal;

public interface PaymentGateway {
    void pay(String token, BigDecimal amount);
}
