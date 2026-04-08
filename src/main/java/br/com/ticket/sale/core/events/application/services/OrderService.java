package br.com.ticket.sale.core.events.application.services;

import br.com.ticket.sale.core.events.application.commands.order.CreateOrderCommand;
import br.com.ticket.sale.core.events.application.commands.spot_reservation.CreateSpotReservationCommand;
import br.com.ticket.sale.core.events.application.gateways.PaymentGateway;
import br.com.ticket.sale.core.events.application.queries.order.ListOrdersQuery;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.domain.entities.order.Order;
import br.com.ticket.sale.core.events.domain.entities.reservation.SpotReservation;
import br.com.ticket.sale.core.events.domain.repositories.CustomerRepository;
import br.com.ticket.sale.core.events.domain.repositories.EventRepository;
import br.com.ticket.sale.core.events.domain.repositories.OrderRepository;
import br.com.ticket.sale.core.events.domain.repositories.SpotReservationRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final EventRepository eventRepo;
    private final SpotReservationRepository spotReservationRepo;
    private final PaymentGateway paymentGateway;

    public OrderService(
            OrderRepository orderRepo,
            CustomerRepository customerRepo,
            EventRepository eventRepo,
            SpotReservationRepository spotReservationRepo,
            PaymentGateway paymentGateway
    ) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.eventRepo = eventRepo;
        this.spotReservationRepo = spotReservationRepo;
        this.paymentGateway = paymentGateway;
    }

    public List<Order> list(ListOrdersQuery query) {
        return orderRepo.findAll();
    }

    @Transactional
    public Order create(CreateOrderCommand command) {

        Customer customer = customerRepo
                .findById(command.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Event event = eventRepo
                .findById(command.eventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.allowReserveSpot(command.eventSectionId(), command.eventSpotId())) {
            throw new RuntimeException("Spot not available");
        }

        SpotReservation reservation =
                SpotReservation.create(
                        new CreateSpotReservationCommand(
                        command.eventSpotId(),
                        command.customerId()
                        )
                );

        try {
            spotReservationRepo.add(reservation);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Spot already reserved");
        }

        EventSection section = event.getSections()
                .stream()
                .filter(s -> s.getId().equals(command.eventSectionId()))
                .findFirst()
                .orElseThrow();

        try {
            paymentGateway.pay(command.cardToken(), section.getPrice());

            Order order = Order.create(
                    command.customerId(),
                    command.eventSpotId(),
                    section.getPrice()
            );

            order.pay();

            orderRepo.add(order);

            event.markSpotAsReserved(command.eventSectionId(), command.eventSpotId());

            eventRepo.add(event);

            return order;

        }catch(Exception e) {
            Order order = Order.create(
                    command.customerId(),
                    command.eventSpotId(),
                    section.getPrice()
            );

            order.cancel();

            orderRepo.add(order);

            throw new RuntimeException("Payment failed", e);
        }
    }
}