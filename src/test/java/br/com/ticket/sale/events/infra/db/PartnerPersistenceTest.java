package br.com.ticket.sale.events.infra.db;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.domain.repositories.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PartnerPersistenceTest {

    @Autowired
    private PartnerRepository repository;

    @Test
    void mustCreateAPartner() {

        Partner partner = new Partner(
                new PartnerConstructorProps(
                        new PartnerId(),
                        new Name("Ticket Company")
                )
        );

        repository.add(partner);

        Partner found =
                repository.findById(partner.getId())
                        .orElse(null);

        assertNotNull(found);
        assertEquals("Ticket Company", found.getName().getValue());
    }
}
