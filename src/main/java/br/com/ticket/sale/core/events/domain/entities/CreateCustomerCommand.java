package br.com.ticket.sale.core.events.domain.entities;

public record CreateCustomerCommand(
        String name,
        String cpf
) {}
