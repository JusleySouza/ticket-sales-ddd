package br.com.ticket.sale.core.events.application.commands.event;

import br.com.ticket.sale.core.common.domain.value_objects.Name;

public record UpdateSectionCommand(
        Name name,
        String description
) {}
