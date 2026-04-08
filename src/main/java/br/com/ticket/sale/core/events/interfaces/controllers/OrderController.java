package br.com.ticket.sale.core.events.interfaces.controllers;

import br.com.ticket.sale.core.events.application.commands.order.CreateOrderCommand;
import br.com.ticket.sale.core.events.application.queries.order.ListOrdersQuery;
import br.com.ticket.sale.core.events.application.services.OrderService;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.domain.entities.order.Order;
import br.com.ticket.sale.core.events.interfaces.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events/{eventId}/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ApiResponse<List<Order>> list() {
        return ApiResponse.success(orderService.list(new ListOrdersQuery()));
    }

    @PostMapping
    public ApiResponse<Order> create(@PathVariable String eventId, @RequestBody Map<String, String> body) {
        CreateOrderCommand command =
                new CreateOrderCommand(
                        new EventId(eventId),
                        new EventSectionId(body.get("sectionId")),
                        new EventSpotId(body.get("spotId")),
                        new CustomerId(body.get("customerId")),
                        body.get("cardToken")
                );

        return ApiResponse.success(orderService.create(command));
    }
}
