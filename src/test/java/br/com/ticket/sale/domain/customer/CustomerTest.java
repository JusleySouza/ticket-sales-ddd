package br.com.ticket.sale.domain.customer;

import br.com.ticket.sale.core.common.domain.value_objects.Cpf;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.CreateCustomerCommand;
import br.com.ticket.sale.core.events.domain.entities.Customer;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    void mustCreateAClient() {

        CreateCustomerCommand command = new CreateCustomerCommand(
                new Name("João"),
                new Cpf("99346413050")
        );

        Customer customer = Customer.create(command);

        assertThat(customer).isNotNull();
        assertThat(customer.getName().getValue()).isEqualTo("João");
        assertThat(customer.getCpf().getValue()).isEqualTo("99346413050");
    }

}
