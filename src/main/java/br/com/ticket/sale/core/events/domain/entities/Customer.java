package br.com.ticket.sale.core.events.domain.entities;

import br.com.ticket.sale.core.common.domain.AggregateRoot;
import br.com.ticket.sale.core.common.domain.value_objects.Cpf;
import br.com.ticket.sale.core.common.domain.value_objects.Name;

import java.util.Map;

public class Customer extends AggregateRoot<CustomerId> {

    private final Cpf cpf;
    private final Name name;

    public Customer(CustomerConstructorProps props) {
        this.id = props.id() == null ? new CustomerId() : props.id();
        this.cpf = props.cpf();
        this.name = props.name();
    }


    public static Customer create(CreateCustomerCommand command) {
        return new Customer(
                new CustomerConstructorProps(
                        new CustomerId(),
                        command.cpf(),
                        command.name()
                )
        );
    }

    @Override
    public Map<String, Object> toJSON() {
        return Map.of(
                "id", id.getValue(),
                "cpf", cpf.getValue(),
                "name", name.getValue()
        );
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Name getName() {
        return name;
    }

}

