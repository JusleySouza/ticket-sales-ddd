package br.com.ticket.sale.events.infra.db;

import br.com.ticket.sale.TicketSaleDddApplication;
import br.com.ticket.sale.core.common.domain.value_objects.Cpf;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.customer.CreateCustomerCommand;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TicketSaleDddApplication.class)
@ActiveProfiles("test")
@Transactional
public class CustomerPersistenceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void mustPersistUpdateAndDeleteCustomer() {

        CreateCustomerCommand command =
                new CreateCustomerCommand(
                        new Name("Customer 1"),
                        new Cpf("703.758.870-91")
                );

        Customer customer = Customer.create(command);

        customerRepository.add(customer);

        entityManager.flush();
        entityManager.clear();

        Optional<Customer> customerFoundOpt =
                customerRepository.findById(customer.getId());

        assertTrue(customerFoundOpt.isPresent());

        Customer customerFound = customerFoundOpt.get();

        assertEquals(customer.getId(), customerFound.getId());
        assertEquals(customer.getName().getValue(), customerFound.getName().getValue());
        assertEquals("70375887091", customerFound.getCpf().getValue());

        customer.changeName(new Name("Customer 2"));

        customerRepository.add(customer);

        entityManager.flush();
        entityManager.clear();

        customerFoundOpt = customerRepository.findById(customer.getId());

        assertTrue(customerFoundOpt.isPresent());

        customerFound = customerFoundOpt.get();

        assertEquals(customer.getId(), customerFound.getId());
        assertEquals(customer.getName().getValue(), customerFound.getName().getValue());

        customerRepository.delete(customer.getId());

        entityManager.flush();
    }
}
