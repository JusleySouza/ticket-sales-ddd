package br.com.ticket.sale.events.application.partner;

import br.com.ticket.sale.core.events.application.commands.partner.CreatePartnerCommand;
import br.com.ticket.sale.core.events.application.commands.partner.UpdatePartnerCommand;
import br.com.ticket.sale.core.events.application.queries.partner.ListPartnersQuery;
import br.com.ticket.sale.core.events.application.services.PartnerService;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.domain.repositories.PartnerRepository;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartnerServiceTest {

    private PartnerRepository partnerRepository;
    private PartnerService partnerService;

    @BeforeEach
    void setup() {
        partnerRepository = Mockito.mock(PartnerRepository.class);
        partnerService = new PartnerService(partnerRepository);
    }

    @Test
    void shouldListPartners() {

        Partner partner1 = Partner.create(new Name("Partner A"));
        Partner partner2 = Partner.create(new Name("Partner B"));

        when(partnerRepository.findAll())
                .thenReturn(List.of(partner1, partner2));

        ListPartnersQuery query = new ListPartnersQuery();

        List<Partner> partners = partnerService.list(query);

        assertEquals(2, partners.size());
        assertEquals("Partner A", partners.get(0).getName().getValue());

        verify(partnerRepository).findAll();
    }

    @Test
    void shouldCreatePartner() {

        CreatePartnerCommand command =
                new CreatePartnerCommand(new Name("New Partner"));

        Partner partner = partnerService.create(command);

        assertNotNull(partner.getId());
        assertEquals("New Partner", partner.getName().getValue());

        verify(partnerRepository).add(partner);
    }

    @Test
    void shouldUpdatePartnerName() {

        PartnerId partnerId = new PartnerId();

        Partner existingPartner =
                Partner.create(new Name("Old Name"));

        when(partnerRepository.findById(partnerId))
                .thenReturn(Optional.of(existingPartner));

        UpdatePartnerCommand command =
                new UpdatePartnerCommand(new Name("Updated Name"));

        Partner updatedPartner =
                partnerService.update(partnerId, command);

        assertEquals("Updated Name",
                updatedPartner.getName().getValue());

        verify(partnerRepository).findById(partnerId);
        verify(partnerRepository).add(existingPartner);
    }

    @Test
    void shouldThrowExceptionWhenPartnerNotFound() {

        PartnerId partnerId = new PartnerId();

        when(partnerRepository.findById(partnerId))
                .thenReturn(Optional.empty());

        UpdatePartnerCommand command =
                new UpdatePartnerCommand(new Name("New Name"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> partnerService.update(partnerId, command)
        );

        assertEquals("Partner not found", exception.getMessage());

        verify(partnerRepository).findById(partnerId);
        verify(partnerRepository, never()).add(any());
    }
}
