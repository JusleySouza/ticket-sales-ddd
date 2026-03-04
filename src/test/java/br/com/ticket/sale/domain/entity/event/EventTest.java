package br.com.ticket.sale.domain.entity.event;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.AddSectionCommand;
import br.com.ticket.sale.core.events.domain.entities.event.CreateEventCommand;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpot;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void mustCreateAnEvent() {

        Event event = Event.create(
                new CreateEventCommand(
                        new Name("Event 1"),
                        "Description of event 1",
                        LocalDateTime.now(),
                        new PartnerId()
                )
        );

        event.addSection(
                new AddSectionCommand(
                        new Name("Event 1"),
                        "Description of session 1",
                        100,
                        BigDecimal.valueOf(1000)
                )
        );

        assertEquals(1, event.getSections().size());
        assertEquals(100, event.getTotalSpots());
        EventSection section = event.getSections().iterator().next();
        assertEquals(100, section.getSpots().size());
    }

    @Test
    void mustPublishAllEventItems() {

        Event event = Event.create(
                new CreateEventCommand(
                        new Name("Event 1"),
                        "Description of event 1",
                        LocalDateTime.now(),
                        new PartnerId()
                )
        );

        event.addSection(
                new AddSectionCommand(
                        new Name("Event 1"),
                        "Description of session 1",
                        100,
                        BigDecimal.valueOf(1000)
                )
        );

        event.addSection(
                new AddSectionCommand(
                        new Name("Event 2"),
                        "Description of session 2",
                        1000,
                        BigDecimal.valueOf(50)
                )
        );

        event.publishAll();

        assertTrue(event.isPublished());

        for (EventSection section : event.getSections()) {
            assertTrue(section.isPublished());

            for (EventSpot spot : section.getSpots()) {
                assertTrue(spot.isPublished());
            }
        }
    }
}
