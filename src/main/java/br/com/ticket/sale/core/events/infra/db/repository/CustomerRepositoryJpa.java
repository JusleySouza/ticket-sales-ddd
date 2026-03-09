package br.com.ticket.sale.core.events.infra.db.repository;

import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.repositories.CustomerRepository;
import br.com.ticket.sale.core.events.infra.persistence.jpa.mappers.CustomerMapper;
import br.com.ticket.sale.core.events.infra.persistence.jpa.repositories.CustomerJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryJpa implements CustomerRepository {

    private final CustomerJpaRepository repository;

    public CustomerRepositoryJpa(CustomerJpaRepository repository){
        this.repository=repository;
    }

    @Override
    public void add(Customer entity){
        repository.save(CustomerMapper.fromDomain(entity));
    }

    @Override
    public Optional<Customer> findById(CustomerId id){
        return repository
                .findById(id.getValue())
                .map(CustomerMapper::toDomain);
    }

    @Override
    public List<Customer> findAll(){
        return repository
                .findAll()
                .stream()
                .map(CustomerMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(CustomerId id){
        repository.deleteById(id.getValue());
    }

}
