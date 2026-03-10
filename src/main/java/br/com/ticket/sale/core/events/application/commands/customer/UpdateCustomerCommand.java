package br.com.ticket.sale.core.events.application.commands.customer;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;

public record UpdateCustomerCommand(
        CustomerId id,
        Name name
) {}
