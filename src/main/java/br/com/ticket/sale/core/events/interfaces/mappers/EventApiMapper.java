package br.com.ticket.sale.core.events.interfaces.mappers;

import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.interfaces.dto.EventResponseDTO;

public class EventApiMapper {

    public static EventResponseDTO toResponse(Event event) {
        return new EventResponseDTO(
                event.getId().getValue(),
                event.getName().getValue(),
                event.getDescription(),
                event.getDate(),
                event.isPublished(),
                event.getTotalSpots(),
                event.getTotalSpotsReserved(),
                event.getPartnerId().getValue()
        );
    }

}
