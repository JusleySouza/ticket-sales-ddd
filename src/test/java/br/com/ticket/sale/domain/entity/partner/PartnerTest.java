package br.com.ticket.sale.domain.entity.partner;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.application.commands.partner.InitEventCommand;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerChangedName;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerCreated;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class PartnerTest {

    @Test
    void mustCreateAnEvent() {

        Name partnerName = new Name("Partner 1");
        Partner partner = Partner.create(partnerName);

        InitEventCommand command = new InitEventCommand(
                new Name("Event 1"),
                "Description of event 1",
                LocalDateTime.now()
        );

        Event event = partner.initEvent(command);

        assertNotNull(event);
        assertEquals("Event 1", event.getName().getValue());
        assertEquals("Description of event 1", event.getDescription());
        assertFalse(event.isPublished());

        assertEquals(partner.getId(), event.getPartnerId());
    }

    @Test
    void shouldGeneratePartnerCreatedEvent() {
        Name name = new Name("Partner 1");

        Partner partner = Partner.create(name);

        var events = partner.getEvents();

        assertEquals(1, events.size());

        var event = events.iterator().next();

        assertInstanceOf(PartnerCreated.class, event);

        PartnerCreated createdEvent = (PartnerCreated) event;

        assertEquals(partner.getId(), createdEvent.getAggregateId());
        assertEquals(name, createdEvent.getName());
    }

    @Test
    void shouldGeneratePartnerChangedNameEvent() {
        Name initialName = new Name("Partner 1");
        Name newName = new Name("Partner Updated");

        Partner partner = Partner.create(initialName);
        partner.clearEvents();

        partner.changeName(newName);

        var events = partner.getEvents();

        assertEquals(1, events.size());

        var event = events.iterator().next();

        assertInstanceOf(PartnerChangedName.class, event);

        PartnerChangedName changedEvent = (PartnerChangedName) event;

        assertEquals(partner.getId(), changedEvent.getAggregateId());
        assertEquals(newName, changedEvent.getName());
    }

    @Test
    void shouldAccumulateEvents() {
        Partner partner = Partner.create(new Name("Partner 1"));

        partner.changeName(new Name("Updated"));

        assertEquals(2, partner.getEvents().size());
    }

    @Test
    void shouldClearEvents() {
        Partner partner = Partner.create(new Name("Partner 1"));

        partner.clearEvents();

        assertTrue(partner.getEvents().isEmpty());
    }

}
