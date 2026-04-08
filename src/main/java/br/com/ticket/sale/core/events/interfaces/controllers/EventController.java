package br.com.ticket.sale.core.events.interfaces.controllers;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.event.CreateEventCommand;
import br.com.ticket.sale.core.events.application.commands.event.UpdateEventCommand;
import br.com.ticket.sale.core.events.application.queries.event.ListEventsQuery;
import br.com.ticket.sale.core.events.application.services.EventService;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.interfaces.api.ApiResponse;
import br.com.ticket.sale.core.events.interfaces.dto.CreateEventDTO;
import br.com.ticket.sale.core.events.interfaces.dto.EventResponseDTO;
import br.com.ticket.sale.core.events.interfaces.dto.UpdateEventDTO;
import br.com.ticket.sale.core.events.interfaces.mappers.EventApiMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ApiResponse<List<Event>> list() {
        return ApiResponse.success(eventService.list(new ListEventsQuery()));
    }

    @PostMapping
    public ApiResponse<EventResponseDTO> create(@RequestBody CreateEventDTO dto) {
        CreateEventCommand command = new CreateEventCommand(
                new Name(dto.name()),
                dto.description(),
                dto.date(),
                new PartnerId(dto.partnerId()));
        Event event = eventService.create(command);
        return ApiResponse.success(EventApiMapper.toResponse(event));
    }

    @PutMapping("/{eventId}")
    public ApiResponse<Event> update(@PathVariable String eventId, @RequestBody UpdateEventDTO dto) {
        UpdateEventCommand command = new UpdateEventCommand(
                dto.name(),
                dto.description(),
                dto.date());
        return ApiResponse.success(eventService.update( new EventId(eventId), command));
    }

    @PutMapping("/{eventId}/publish-all")
    public ApiResponse<Event> publish(@PathVariable String eventId) {
        return ApiResponse.success(eventService.publishAll(new EventId(eventId)));
    }
}
