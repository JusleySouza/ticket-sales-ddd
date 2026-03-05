package br.com.ticket.sale.core.events.infra.db.repository;

import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerRepository;
import br.com.ticket.sale.core.events.infra.persistence.jpa.mappers.PartnerMapper;
import br.com.ticket.sale.core.events.infra.persistence.jpa.repositories.PartnerJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PartnerRepositoryJpa implements PartnerRepository {

    private final PartnerJpaRepository repository;

    public PartnerRepositoryJpa(PartnerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(Partner partner) {
        repository.save(PartnerMapper.fromDomain(partner));
    }

    @Override
    public Optional<Partner> findById(PartnerId id) {
        return repository
                .findById(id.getValue())
                .map(PartnerMapper::toDomain);
    }

    @Override
    public List<Partner> findAll() {
        return repository
                .findAll()
                .stream()
                .map(PartnerMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(PartnerId id) {
        repository.deleteById(id.getValue());
    }
}
