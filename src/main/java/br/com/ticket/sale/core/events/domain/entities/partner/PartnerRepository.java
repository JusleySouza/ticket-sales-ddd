package br.com.ticket.sale.core.events.domain.entities.partner;

import br.com.ticket.sale.core.common.domain.IRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository  extends IRepository<Partner, PartnerId> {

    void add(Partner partner);

    Optional<Partner> findById(PartnerId id);

    List<Partner> findAll();

    void delete(PartnerId id);
}
