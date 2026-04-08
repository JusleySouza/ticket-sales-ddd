package br.com.ticket.sale.events.infra.db;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.event.AddSectionCommand;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.application.commands.partner.InitEventCommand;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.domain.repositories.EventRepository;
import br.com.ticket.sale.core.events.domain.repositories.PartnerRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EventPersistenceTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void mustPersistFullEventAggregate() {
        //step1:
        Partner partner = new Partner(
                new PartnerConstructorProps(
                        new PartnerId(),
                        new Name("Partner 1")
                )
        );
        partnerRepository.add(partner);

        //step2:
        Event event = partner.initEvent(
                new InitEventCommand(
                        new Name("Rock Festival"),
                        "Big rock festival",
                        LocalDateTime.now().plusDays(10)
                )
        );

       //step3:
        event.addSection(
                new AddSectionCommand(
                        new Name("VIP Area"),
                        "VIP seating",
                        5,
                        BigDecimal.valueOf(200)
                )
        );

        event.addSection(
                new AddSectionCommand(
                        new Name("Regular Area"),
                        "Regular seating",
                        10,
                        BigDecimal.valueOf(100)
                )
        );

        //step4:
        eventRepository.add(event);

        //step5:
        entityManager.flush();
        entityManager.clear();

        //step6:
        Optional<Event> result = eventRepository.findById(event.getId());
        assertTrue(result.isPresent());
        Event persistedEvent = result.get();

        //step7:
        assertEquals("Rock Festival", persistedEvent.getName().getValue());
        assertEquals("Big rock festival", persistedEvent.getDescription());

        //step8:
        assertEquals(partner.getId(), persistedEvent.getPartnerId());

        //step9:
        assertEquals(2, persistedEvent.getSections().size());

        //step10:
        int totalSpots =
                persistedEvent.getSections()
                        .stream()
                        .mapToInt(section -> section.getSpots().size())
                        .sum();
        assertEquals(15, totalSpots);

        //step11:
        assertEquals( persistedEvent.getTotalSpots(), totalSpots );
    }
}