package br.com.ticket.sale.core.events.domain.entities.partner;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository {

    void add(Partner partner);

    Optional<Partner> findById(PartnerId id);

    List<Partner> findAll();

    void delete(PartnerId id);
}
