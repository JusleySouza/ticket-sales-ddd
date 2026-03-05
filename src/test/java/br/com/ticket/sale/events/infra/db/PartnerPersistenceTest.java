package br.com.ticket.sale.events.infra.db;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.PartnerJpaEntity;
import br.com.ticket.sale.core.events.infra.persistence.jpa.repositories.PartnerJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PartnerPersistenceTest {

    @Autowired
    private PartnerJpaRepository repository;

    @Test
    void mustCreateAPartner() {

        Partner partner = new Partner(
                new PartnerConstructorProps(
                        new PartnerId(),
                        new Name("Ticket Company")
                )
        );

        PartnerJpaEntity entity = new PartnerJpaEntity();
        entity.setId(partner.getId().getValue());
        entity.setName(partner.getName().getValue());

        repository.save(entity);

        PartnerJpaEntity found =
                repository.findById(partner.getId().getValue())
                        .orElse(null);

        assertNotNull(found);
        assertEquals("Ticket Company", found.getName());
    }
}
