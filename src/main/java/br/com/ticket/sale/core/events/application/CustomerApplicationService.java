package br.com.ticket.sale.core.events.application;

import br.com.ticket.sale.core.common.domain.value_objects.Cpf;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.customer.CreateCustomerCommand;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerApplicationService {

    private final CustomerRepository customerRepository;

    public CustomerApplicationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer register(String name, String cpf) {

        CreateCustomerCommand command =
                new CreateCustomerCommand(
                        new Name(name),
                        new Cpf(cpf)
                );

        Customer customer = Customer.create(command);

        customerRepository.add(customer);

        return customer;
    }
}
