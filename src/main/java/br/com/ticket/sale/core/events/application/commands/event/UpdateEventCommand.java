package br.com.ticket.sale.core.events.application.commands.event;

import java.time.LocalDateTime;

public record UpdateEventCommand(
        String name,
        String description,
        LocalDateTime date
) {
}
