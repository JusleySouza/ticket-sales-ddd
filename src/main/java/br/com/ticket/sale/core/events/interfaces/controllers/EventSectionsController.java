package br.com.ticket.sale.core.events.interfaces.controllers;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.event.AddSectionCommand;
import br.com.ticket.sale.core.events.application.commands.event.UpdateSectionCommand;
import br.com.ticket.sale.core.events.application.queries.event.FindSectionsQuery;
import br.com.ticket.sale.core.events.application.services.EventService;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.interfaces.api.ApiResponse;
import br.com.ticket.sale.core.events.interfaces.dto.CreateEventSectionDTO;
import br.com.ticket.sale.core.events.interfaces.dto.EventSectionResponseDTO;
import br.com.ticket.sale.core.events.interfaces.mappers.EventSectionApiMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events/{eventId}/sections")
public class EventSectionsController {

    private final EventService eventService;

    public EventSectionsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ApiResponse<List<EventSection>> list(@PathVariable String eventId) {
        return ApiResponse.success(eventService.findSections( new EventId(eventId), new FindSectionsQuery()));
    }

    @PostMapping
    public ApiResponse<EventSectionResponseDTO> create(@PathVariable String eventId, @RequestBody CreateEventSectionDTO dto) {
        AddSectionCommand command =
                new AddSectionCommand(
                        new Name(dto.name()),
                        dto.description(),
                        dto.totalSpots(),
                        new BigDecimal(String.valueOf(dto.price())));
        Event event = eventService.addSection( new EventId(eventId), command);
        return ApiResponse.success(EventSectionApiMapper.toResponse(event,
                event.getSections().stream().filter(s -> s.getName().getValue().equals(dto.name())).findFirst().orElseThrow()));
    }

    @PutMapping("/{sectionId}")
    public ApiResponse<List<EventSection>> update(
            @PathVariable String eventId,
            @PathVariable String sectionId,
            @RequestBody Map<String, String> body
    ) {

        UpdateSectionCommand command =
                new UpdateSectionCommand(
                        body.get("name") != null ? new Name(body.get("name")) : null,
                        body.get("description")
                );

        return ApiResponse.success(eventService.updateSection( new EventId(eventId), new EventSectionId(sectionId), command));
    }
}
