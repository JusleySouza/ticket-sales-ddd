package br.com.ticket.sale.core.events.application.services;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.event.AddSectionCommand;
import br.com.ticket.sale.core.events.application.commands.event.CreateEventCommand;
import br.com.ticket.sale.core.events.application.commands.event.UpdateEventCommand;
import br.com.ticket.sale.core.events.application.commands.event.UpdateSectionCommand;
import br.com.ticket.sale.core.events.application.commands.event.UpdateLocationCommand;
import br.com.ticket.sale.core.events.application.commands.partner.InitEventCommand;
import br.com.ticket.sale.core.events.application.queries.event.FindSectionsQuery;
import br.com.ticket.sale.core.events.application.queries.event.FindSpotsQuery;
import br.com.ticket.sale.core.events.application.queries.event.ListEventsQuery;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpot;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.repositories.EventRepository;
import br.com.ticket.sale.core.events.domain.repositories.PartnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepo;
    private final PartnerRepository partnerRepo;

    public EventService(EventRepository eventRepo, PartnerRepository partnerRepo) {
        this.eventRepo = eventRepo;
        this.partnerRepo = partnerRepo;
    }

    public List<Event> list(ListEventsQuery query) {
        return eventRepo.findAll();
    }

    public List<EventSection> findSections(EventId eventId, FindSectionsQuery query) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return event.getSections().stream().toList();
    }

    public List<EventSpot> findSpots(
            EventId eventId,
            EventSectionId sectionId,
            FindSpotsQuery query
    ) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventSection section = event.getSections()
                .stream()
                .filter(s -> s.getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Section not found"));

        return section.getSpots().stream().toList();
    }

    @Transactional
    public Event create(CreateEventCommand command) {

        Partner partner = partnerRepo.findById(command.partnerId())
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        InitEventCommand initCommand = new InitEventCommand(
                command.name(),
                command.description(),
                command.date()
        );

        Event event = partner.initEvent(initCommand);

        eventRepo.add(event);

        return event;
    }

    @Transactional
    public Event update(EventId eventId, UpdateEventCommand command) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (command.name() != null) {
            event.changeName(new Name(command.name()));
        }

        if (command.description() != null) {
            event.changeDescription(command.description());
        }

        if (command.date() != null) {
            event.changeDate(command.date());
        }

        eventRepo.add(event);

        return event;
    }

    @Transactional
    public Event addSection(EventId eventId, AddSectionCommand command) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.addSection(command);

        eventRepo.add(event);

        return event;
    }

    @Transactional
    public List<EventSection> updateSection(
            EventId eventId,
            EventSectionId sectionId,
            UpdateSectionCommand command
    ) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventSection section = event.getSections()
                .stream()
                .filter(s -> s.getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Section not found"));

        if (command.name() != null) {
            section.changeName(command.name());
        }

        if (command.description() != null) {
            section.changeDescription(command.description());
        }

        eventRepo.add(event);

        return event.getSections().stream().toList();
    }

    @Transactional
    public EventSpot updateLocation(
            EventId eventId,
            EventSectionId sectionId,
            EventSpotId spotId,
            UpdateLocationCommand command
    ) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventSection section = event.getSections()
                .stream()
                .filter(s -> s.getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Section not found"));

        section.changeLocation(spotId, command.location());

        eventRepo.add(event);

        return section.getSpots()
                .stream()
                .filter(spot -> spot.getId().equals(spotId))
                .findFirst()
                .orElseThrow();
    }

    @Transactional
    public Event publishAll(EventId eventId) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.publishAll();

        eventRepo.add(event);

        return event;
    }
}