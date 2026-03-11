package br.com.ticket.sale.events.application.order;

import br.com.ticket.sale.core.events.application.commands.order.CreateOrderCommand;
import br.com.ticket.sale.core.events.application.services.OrderService;
import br.com.ticket.sale.core.events.domain.entities.customer.Customer;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.domain.entities.order.Order;
import br.com.ticket.sale.core.events.domain.repositories.CustomerRepository;
import br.com.ticket.sale.core.events.domain.repositories.EventRepository;
import br.com.ticket.sale.core.events.domain.repositories.OrderRepository;
import br.com.ticket.sale.core.events.domain.repositories.SpotReservationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private EventRepository eventRepository;
    private SpotReservationRepository spotReservationRepository;

    private OrderService orderService;

    @BeforeEach
    void setup() {

        orderRepository = mock(OrderRepository.class);
        customerRepository = mock(CustomerRepository.class);
        eventRepository = mock(EventRepository.class);
        spotReservationRepository = mock(SpotReservationRepository.class);

        orderService = new OrderService(
                orderRepository,
                customerRepository,
                eventRepository,
                spotReservationRepository
        );
    }

    @Test
    void mustCreateOrderSuccessfully() {
        CustomerId customerId = new CustomerId();
        EventId eventId = new EventId();
        EventSectionId sectionId = new EventSectionId();
        EventSpotId spotId = new EventSpotId();

        CreateOrderCommand command = new CreateOrderCommand( eventId, sectionId, spotId, customerId );

        Customer customer = mock(Customer.class);
        Event event = mock(Event.class);
        EventSection section = mock(EventSection.class);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        when(event.allowReserveSpot(sectionId, spotId)).thenReturn(true);

        when(event.getSections()).thenReturn(Set.of(section));

        when(section.getId()).thenReturn(sectionId);
        when(section.getPrice()).thenReturn(BigDecimal.valueOf(100));

        Order order = orderService.create(command);

        assertNotNull(order);

        verify(spotReservationRepository, times(1)).add(any());
        verify(orderRepository, times(1)).add(any());
        verify(eventRepository, times(1)).add(event);
        verify(event, times(1)).markSpotAsReserved(sectionId, spotId);
    }

    @Test
    void mustThrowExceptionWhenCustomerNotFound() {
        CustomerId customerId = new CustomerId();
        EventId eventId = new EventId();
        EventSectionId sectionId = new EventSectionId();
        EventSpotId spotId = new EventSpotId();

        CreateOrderCommand command = new CreateOrderCommand( eventId, sectionId, spotId, customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.create(command));
        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    void mustThrowExceptionWhenSpotIsNotAvailable() {
        CustomerId customerId = new CustomerId();
        EventId eventId = new EventId();
        EventSectionId sectionId = new EventSectionId();
        EventSpotId spotId = new EventSpotId();

        CreateOrderCommand command =
                new CreateOrderCommand( eventId, sectionId, spotId, customerId );

        Customer customer = mock(Customer.class);
        Event event = mock(Event.class);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        when(event.allowReserveSpot(sectionId, spotId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.create(command));
        assertEquals("Spot not available", exception.getMessage());
    }
}
