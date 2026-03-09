package br.com.ticket.sale.core.events.infra.persistence.jpa.mappers;

import br.com.ticket.sale.core.common.domain.value_objects.Cpf;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.CustomerJpaEntity;

public class CustomerMapper {

    public static CustomerJpaEntity fromDomain(Customer customer) {
        CustomerJpaEntity entity = new CustomerJpaEntity();
        entity.setId(customer.getId().getValue());
        entity.setCpf(customer.getCpf().getValue());
        entity.setName(customer.getName().getValue());
        return entity;
    }

    public static Customer toDomain(CustomerJpaEntity entity) {
        CustomerConstructorProps props =
                new CustomerConstructorProps(
                        new CustomerId(entity.getId()),
                        new Cpf(entity.getCpf()),
                        new Name(entity.getName())
                );
        return new Customer(props);
    }

}
