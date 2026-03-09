package br.com.ticket.sale.core.events.domain.repositories;

import br.com.ticket.sale.core.common.domain.IRepository;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository  extends IRepository<Partner, PartnerId> {

    void add(Partner partner);

    Optional<Partner> findById(PartnerId id);

    List<Partner> findAll();

    void delete(PartnerId id);
}
