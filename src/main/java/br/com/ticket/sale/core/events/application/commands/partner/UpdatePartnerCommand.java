package br.com.ticket.sale.core.events.application.commands.partner;

import br.com.ticket.sale.core.common.domain.value_objects.Name;

public record UpdatePartnerCommand(
        Name name
) {}
