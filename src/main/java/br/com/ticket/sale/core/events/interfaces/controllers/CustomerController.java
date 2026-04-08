package br.com.ticket.sale.core.events.interfaces.controllers;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.customer.UpdateCustomerCommand;
import br.com.ticket.sale.core.events.application.services.CustomerService;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.interfaces.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ApiResponse<List<Customer>> list() {
        return ApiResponse.success(customerService.list());
    }

    @PostMapping
    public ApiResponse<Customer> create(@RequestBody Map<String, String> body) {
        return ApiResponse.success(customerService.register(
                body.get("name"),
                body.get("cpf")
        ));
    }

    @PutMapping("/{customerId}")
    public ApiResponse<Customer> update(@PathVariable String customerId, @RequestBody Map<String, String> body) {
        UpdateCustomerCommand command = new UpdateCustomerCommand(
                new CustomerId(customerId),
                body.get("name") != null ? new Name(body.get("name")) : null
        );

        return ApiResponse.success(customerService.update(command));
    }
}
