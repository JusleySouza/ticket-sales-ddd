package br.com.ticket.sale.events.application.event;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.event.*;
import br.com.ticket.sale.core.events.application.queries.event.FindSectionsQuery;
import br.com.ticket.sale.core.events.application.queries.event.FindSpotsQuery;
import br.com.ticket.sale.core.events.application.queries.event.ListEventsQuery;
import br.com.ticket.sale.core.events.application.services.EventService;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpot;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.repositories.EventRepository;
import br.com.ticket.sale.core.events.domain.repositories.PartnerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    private EventRepository eventRepository;
    private PartnerRepository partnerRepository;
    private EventService eventService;

    @BeforeEach
    void setup() {
        eventRepository = mock(EventRepository.class);
        partnerRepository = mock(PartnerRepository.class);
        eventService = new EventService(eventRepository, partnerRepository);
    }

    @Test
    void shouldListEvents() {

        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);

        when(eventRepository.findAll())
                .thenReturn(List.of(event1, event2));

        List<Event> events = eventService.list(new ListEventsQuery());

        assertEquals(2, events.size());

        verify(eventRepository).findAll();
    }

    @Test
    void shouldCreateEvent() {

        Partner partner = Partner.create(new Name("Partner"));

        CreateEventCommand command = new CreateEventCommand(
                new Name("Event"),
                "description",
                LocalDateTime.now(),
                partner.getId()
        );

        when(partnerRepository.findById(command.partnerId()))
                .thenReturn(Optional.of(partner));

        Event event = eventService.create(command);

        assertNotNull(event);
        assertEquals("Event", event.getName().getValue());

        verify(eventRepository).add(event);
    }

    @Test
    void shouldUpdateEvent() {

        EventId eventId = new EventId();

        Event event = mock(Event.class);

        when(eventRepository.findById(eventId))
                .thenReturn(Optional.of(event));

        UpdateEventCommand command = new UpdateEventCommand(
                "New Name",
                "New Description",
                LocalDateTime.now()
        );

        Event updated = eventService.update(eventId, command);

        verify(event).changeName(any());
        verify(event).changeDescription("New Description");
        verify(event).changeDate(command.date());

        verify(eventRepository).add(event);

        assertEquals(event, updated);
    }

    @Test
    void shouldAddSectionToEvent() {

        EventId eventId = new EventId();

        Event event = mock(Event.class);

        when(eventRepository.findById(eventId))
                .thenReturn(Optional.of(event));

        AddSectionCommand command = new AddSectionCommand(
                new Name("VIP"),
                "VIP area",
                10,
                BigDecimal.valueOf(200)
        );

        Event result = eventService.addSection(eventId, command);

        verify(event).addSection(command);
        verify(eventRepository).add(event);

        assertEquals(event, result);
    }

    @Test
    void shouldFindSections() {

        EventId eventId = new EventId();

        EventSection section = mock(EventSection.class);
        Event event = mock(Event.class);

        when(event.getSections()).thenReturn(java.util.Set.of(section));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        List<EventSection> sections =
                eventService.findSections(eventId, new FindSectionsQuery());

        assertEquals(1, sections.size());

        verify(eventRepository).findById(eventId);
    }

    @Test
    void shouldFindSpots() {

        EventId eventId = new EventId();
        EventSectionId sectionId = new EventSectionId();

        EventSpot spot = mock(EventSpot.class);

        EventSection section = mock(EventSection.class);
        when(section.getId()).thenReturn(sectionId);
        when(section.getSpots()).thenReturn(java.util.Set.of(spot));

        Event event = mock(Event.class);
        when(event.getSections()).thenReturn(java.util.Set.of(section));

        when(eventRepository.findById(eventId))
                .thenReturn(Optional.of(event));

        List<EventSpot> spots =
                eventService.findSpots(eventId, sectionId, new FindSpotsQuery());

        assertEquals(1, spots.size());
    }

    @Test
    void shouldPublishEvent() {

        EventId eventId = new EventId();

        Event event = mock(Event.class);

        when(eventRepository.findById(eventId))
                .thenReturn(Optional.of(event));

        Event result = eventService.publishAll(eventId);

        verify(event).publishAll();
        verify(eventRepository).add(event);

        assertEquals(event, result);
    }

}
