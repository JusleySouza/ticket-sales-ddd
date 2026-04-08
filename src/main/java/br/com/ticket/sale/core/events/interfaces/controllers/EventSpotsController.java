package br.com.ticket.sale.core.events.interfaces.controllers;

import br.com.ticket.sale.core.events.application.commands.event.UpdateLocationCommand;
import br.com.ticket.sale.core.events.application.queries.event.FindSpotsQuery;
import br.com.ticket.sale.core.events.application.services.EventService;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpot;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.interfaces.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events/{eventId}/sections/{sectionId}/spots")
public class EventSpotsController {

    private final EventService eventService;

    public EventSpotsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ApiResponse<List<EventSpot>> list( @PathVariable String eventId, @PathVariable String sectionId) {
        return ApiResponse.success(eventService.findSpots(new EventId(eventId), new EventSectionId(sectionId), new FindSpotsQuery()));
    }

    @PutMapping("/{spotId}")
    public ApiResponse<EventSpot> update(
            @PathVariable String eventId,
            @PathVariable String sectionId,
            @PathVariable String spotId,
            @RequestBody Map<String, String> body
    ) {

        UpdateLocationCommand command = new UpdateLocationCommand(body.get("location"));

        return ApiResponse.success(eventService.updateLocation(new EventId(eventId), new EventSectionId(sectionId),
                new EventSpotId(spotId), command));
    }
}
