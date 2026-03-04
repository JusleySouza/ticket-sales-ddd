package br.com.ticket.sale.domain.entity.partner;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.partner.InitEventCommand;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

        System.out.println(event.toJSON());

        assertNotNull(event);
        assertEquals("Event 1", event.getName().getValue());
        assertEquals("Description of event 1", event.getDescription());
        assertFalse(event.isPublished());
    }
}
