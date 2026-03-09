package br.com.ticket.sale.events.application.customer;

import br.com.ticket.sale.core.common.domain.value_objects.Cpf;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.CustomerApplicationService;
import br.com.ticket.sale.core.events.domain.entities.customer.CreateCustomerCommand;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.repositories.CustomerRepository;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CustomerApplicationServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerApplicationService customerService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void mustListCustomers() {
        Customer customer = Customer.create(
                new CreateCustomerCommand(
                        new Name("Customer 1"),
                        new Cpf("70375887091")
                )
        );
        customerRepository.add(customer);

        entityManager.flush();
        entityManager.clear();

        List<Customer> customers = customerService.list();
        assertEquals(1, customers.size());
    }

    @Test
    void mustRegisterCustomer() {
        Customer customer =
                customerService.register(
                        "Customer 1",
                        "70375887091"
                );

        entityManager.flush();
        entityManager.clear();

        List<Customer> customers = customerRepository.findAll();
        assertEquals(1, customers.size());
        Customer persistedCustomer = customers.get(0);

        assertEquals("Customer 1", persistedCustomer.getName().getValue());
        assertEquals("70375887091", persistedCustomer.getCpf().getValue());
    }
}
