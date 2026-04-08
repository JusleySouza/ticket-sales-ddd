package br.com.ticket.sale.events.application.order;

import br.com.ticket.sale.core.events.application.commands.order.CreateOrderCommand;
import br.com.ticket.sale.core.events.application.gateways.PaymentGateway;
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
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

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

    private PaymentGateway paymentGateway;

    private OrderService orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        orderRepository = mock(OrderRepository.class);
        customerRepository = mock(CustomerRepository.class);
        eventRepository = mock(EventRepository.class);
        spotReservationRepository = mock(SpotReservationRepository.class);
        paymentGateway = mock(PaymentGateway.class);

        orderService = new OrderService(
                orderRepository,
                customerRepository,
                eventRepository,
                spotReservationRepository,
                paymentGateway
        );
    }

    @Test
    void mustCreateOrderSuccessfully() {
        CustomerId customerId = new CustomerId();
        EventId eventId = new EventId();
        EventSectionId sectionId = new EventSectionId();
        EventSpotId spotId = new EventSpotId();
        String cardToken = "card-token-123";

        CreateOrderCommand command = new CreateOrderCommand(eventId, sectionId, spotId, customerId, cardToken);

        Customer customer = mock(Customer.class);
        Event event = mock(Event.class);
        EventSection section = mock(EventSection.class);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(event.allowReserveSpot(sectionId, spotId)).thenReturn(true);
        when(event.getSections()).thenReturn(Set.of(section));

        when(section.getId()).thenReturn(sectionId);
        when(section.getPrice()).thenReturn(BigDecimal.valueOf(100));

        doNothing().when(paymentGateway).pay(anyString(), any());

        Order order = orderService.create(command);

        assertNotNull(order);

        verify(paymentGateway, times(1)).pay(eq(cardToken), eq(BigDecimal.valueOf(100)));

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

        CreateOrderCommand command = new CreateOrderCommand(eventId, sectionId, spotId, customerId, "token");

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

        CreateOrderCommand command = new CreateOrderCommand(eventId, sectionId, spotId, customerId, "token");

        Customer customer = mock(Customer.class);
        Event event = mock(Event.class);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(event.allowReserveSpot(sectionId, spotId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.create(command));
        assertEquals("Spot not available", exception.getMessage());
    }

    @Test
    void mustThrowExceptionWhenSpotAlreadyReserved() {
        CustomerId customerId = new CustomerId();
        EventId eventId = new EventId();
        EventSectionId sectionId = new EventSectionId();
        EventSpotId spotId = new EventSpotId();

        CreateOrderCommand command = new CreateOrderCommand(eventId, sectionId, spotId, customerId, "token");

        Customer customer = mock(Customer.class);
        Event event = mock(Event.class);
        EventSection section = mock(EventSection.class);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(event.allowReserveSpot(sectionId, spotId)).thenReturn(true);
        when(event.getSections()).thenReturn(Set.of(section));

        when(section.getId()).thenReturn(sectionId);

        doThrow(new DataIntegrityViolationException("error")).when(spotReservationRepository).add(any());

        RuntimeException exception = assertThrows( RuntimeException.class, () -> orderService.create(command));
        assertEquals("Spot already reserved", exception.getMessage());
    }

    @Test
    void mustCancelOrderWhenPaymentFails() {
        CustomerId customerId = new CustomerId();
        EventId eventId = new EventId();
        EventSectionId sectionId = new EventSectionId();
        EventSpotId spotId = new EventSpotId();

        String cardToken = "card-token-123";

        CreateOrderCommand command = new CreateOrderCommand(eventId, sectionId, spotId, customerId, cardToken);

        Customer customer = mock(Customer.class);
        Event event = mock(Event.class);
        EventSection section = mock(EventSection.class);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(event.allowReserveSpot(sectionId, spotId)).thenReturn(true);
        when(event.getSections()).thenReturn(Set.of(section));

        when(section.getId()).thenReturn(sectionId);
        when(section.getPrice()).thenReturn(BigDecimal.valueOf(100));

        doThrow(new RuntimeException("Payment error")).when(paymentGateway).pay(anyString(), any());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.create(command));

        assertEquals("Payment failed", exception.getMessage());

        verify(orderRepository, times(1)).add(any());
    }
}
