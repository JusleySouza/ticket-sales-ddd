package br.com.ticket.sale.core.events.domain.entities.partner;

import br.com.ticket.sale.core.common.domain.value_objects.Name;

import java.time.LocalDateTime;

public record InitEventCommand(
        Name name,
        String description,
        LocalDateTime date
) {}
