package br.com.ticket.sale.core.events.application.services;

import br.com.ticket.sale.core.events.application.commands.partner.CreatePartnerCommand;
import br.com.ticket.sale.core.events.application.commands.partner.UpdatePartnerCommand;
import br.com.ticket.sale.core.events.application.queries.partner.ListPartnersQuery;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.domain.repositories.PartnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepo;

    public PartnerService(PartnerRepository partnerRepo) {
        this.partnerRepo = partnerRepo;
    }

    public List<Partner> list(ListPartnersQuery query) {
        return partnerRepo.findAll();
    }

    @Transactional
    public Partner create(CreatePartnerCommand command) {
        Partner partner = Partner.create(
                command.name()
        );
        partnerRepo.add(partner);
        return partner;
    }

    @Transactional
    public Partner update(PartnerId partnerId, UpdatePartnerCommand command) {
        Partner partner = partnerRepo.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        if (command.name() != null) {
            partner.changeName(command.name());
        }
        partnerRepo.add(partner);
        return partner;
    }
}
