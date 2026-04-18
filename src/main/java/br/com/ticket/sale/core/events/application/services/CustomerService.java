package br.com.ticket.sale.core.events.application.services;

import br.com.ticket.sale.core.common.application.ApplicationService;
import br.com.ticket.sale.core.common.domain.value_objects.Cpf;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.customer.CreateCustomerCommand;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.application.commands.customer.UpdateCustomerCommand;
import br.com.ticket.sale.core.events.domain.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationService applicationService;

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
        applicationService.commit(customer);
        return customer;
    }

    @Transactional
    public Customer update(UpdateCustomerCommand command) {
        Customer customer = customerRepository
                .findById(command.id())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (command.name() != null) {
            customer.changeName(command.name());
        }
        customerRepository.add(customer);
        applicationService.commit(customer);
        return customer;
    }
}
