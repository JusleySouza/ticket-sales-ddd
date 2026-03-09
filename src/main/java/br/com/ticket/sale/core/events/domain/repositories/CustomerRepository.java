package br.com.ticket.sale.core.events.domain.repositories;

import br.com.ticket.sale.core.common.domain.IRepository;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends IRepository<Customer, CustomerId> {

    void add(Customer entity);

    Optional<Customer> findById(CustomerId id);

    List<Customer> findAll();

    void delete(CustomerId id);

}
