package br.com.ticket.sale.core.events.domain.entities;

public record CustomerConstructorProps(
        String id,
        String cpf,
        String name
) {}
