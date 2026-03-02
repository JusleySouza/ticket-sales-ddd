package br.com.ticket.sale.core.events.domain.entities;

import br.com.ticket.sale.core.common.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Customer extends AggregateRoot {

    private String id;
    private String cpf;
    private String name;

    public Customer(CustomerConstructorProps props) {
        super();
        this.id = props.id() != null ? props.id() : UUID.randomUUID().toString();
        this.cpf = props.cpf();
        this.name = props.name();
    }


    public static Customer create(CreateCustomerCommand command) {
        return new Customer(
                new CustomerConstructorProps(
                        null,
                        command.cpf(),
                        command.name()
                )
        );
    }

    @Override
    public Map<String, Object> toJSON() {
        return Map.of(
                "id", this.id,
                "cpf", this.cpf,
                "name", this.name
        );
    }

}

