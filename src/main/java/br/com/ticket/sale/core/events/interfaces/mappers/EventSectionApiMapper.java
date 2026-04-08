package br.com.ticket.sale.core.events.interfaces.mappers;

import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.interfaces.dto.EventSectionResponseDTO;

public class EventSectionApiMapper {

    public static EventSectionResponseDTO toResponse(Event event, EventSection eventSection) {
        return new EventSectionResponseDTO(
                event.getId().getValue(),
                event.getName().getValue(),
                event.getDescription(),
                event.isPublished(),
                event.getTotalSpots(),
                event.getTotalSpotsReserved(),
                eventSection.getPrice()
        );
    }

}
