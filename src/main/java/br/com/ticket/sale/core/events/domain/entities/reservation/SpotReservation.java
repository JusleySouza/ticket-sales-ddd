package br.com.ticket.sale.core.events.domain.entities.reservation;

import br.com.ticket.sale.core.common.domain.entity.AggregateRoot;
import br.com.ticket.sale.core.events.application.commands.spot_reservation.CreateSpotReservationCommand;
import br.com.ticket.sale.core.events.domain.entities.customer.CustomerId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;

import java.time.LocalDateTime;
import java.util.Map;

// Representa uma reserva de spot para um evento, associada a um cliente e a um spot específico.
public class SpotReservation extends AggregateRoot<EventSpotId> {

    private EventSpotId spotId;
    private CustomerId customerId;
    private LocalDateTime reservationDate;

    public SpotReservation(
            EventSpotId spotId,
            CustomerId customerId,
            LocalDateTime reservationDate
    ) {
        this.spotId = spotId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
    }

    public static SpotReservation create( CreateSpotReservationCommand command) {
        return new SpotReservation(
                command.eventSpotId(),
                command.customerId(),
                LocalDateTime.now()
        );
    }

    public void changeReservation(CustomerId customerId) {
        this.customerId = customerId;
        this.reservationDate = LocalDateTime.now();
    }

    public EventSpotId getSpotId() { return spotId; }

    public CustomerId getCustomerId() { return customerId; }

    public LocalDateTime getReservationDate() { return reservationDate; }

    @Override
    public Map<String, Object> toJSON() {
        return Map.of(
                "spotId", spotId.getValue(),
                "customerId", customerId.getValue(),
                "reservationDate", reservationDate
        );
    }
}
