package br.com.ticket.sale.core.events.domain.entities;

import br.com.ticket.sale.core.common.domain.value_objects.Cpf;
import br.com.ticket.sale.core.common.domain.value_objects.Name;

public record CustomerConstructorProps(
        CustomerId id,
        Cpf cpf,
        Name name
) {}
