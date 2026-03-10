package br.com.ticket.sale.events.application.customer;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.customer.UpdateCustomerCommand;
import br.com.ticket.sale.core.events.application.services.CustomerService;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void mustListCustomers() {

        Customer customer1 = mock(Customer.class);
        Customer customer2 = mock(Customer.class);

        when(customerRepository.findAll())
                .thenReturn(List.of(customer1, customer2));

        List<Customer> customers = customerService.list();

        assertEquals(2, customers.size());

        verify(customerRepository).findAll();
    }

    @Test
    void mustRegisterCustomer() {

        Customer customer = customerService.register(
                "Customer 1",
                "70375887091"
        );

        assertNotNull(customer);
        assertEquals("Customer 1", customer.getName().getValue());
        assertEquals("70375887091", customer.getCpf().getValue());

        verify(customerRepository).add(customer);
    }

    @Test
    void mustUpdateCustomer() {

        CustomerId customerId = new CustomerId();

        Customer customer = mock(Customer.class);

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        UpdateCustomerCommand command =
                new UpdateCustomerCommand(
                        customerId,
                        new Name("Updated Name")
                );

        Customer result = customerService.update(command);

        verify(customerRepository).findById(customerId);
        verify(customer).changeName(new Name("Updated Name"));

        assertEquals(customer, result);
    }

    @Test
    void mustThrowExceptionWhenCustomerNotFound() {

        CustomerId customerId = new CustomerId();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        UpdateCustomerCommand command =
                new UpdateCustomerCommand(
                        customerId,
                        new Name("New Name")
                );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.update(command)
        );

        assertEquals("Customer not found", exception.getMessage());

        verify(customerRepository).findById(customerId);
        verify(customerRepository, never()).add(any());
    }
}
