package br.com.ticket.sale.domain.customer;

import br.com.ticket.sale.core.common.domain.value_objects.Cpf;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.customer.CreateCustomerCommand;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.ticket.sale.core.events.domain.entities.customer.CustomerConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    void mustCreateAClient() {

        CreateCustomerCommand command = new CreateCustomerCommand(
                new Name("João"),
                new Cpf("99346413050")
        );

        Customer customer = Customer.create(command);

        System.out.println(customer.getId());

        assertThat(customer).isInstanceOf(Customer.class);
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getId()).isInstanceOf(CustomerId.class);
        assertThat(customer.getName().getValue()).isEqualTo("João");
        assertThat(customer.getCpf().getValue()).isEqualTo("99346413050");

        Customer customer2 = new Customer(
                new CustomerConstructorProps(
                        new CustomerId(customer.getId().getValue()),
                        new Cpf("703.758.870-91"),
                        new Name("João Pedro")
                )
        );

        assertThat(customer.equals(customer2)).isTrue();
    }

}
