package br.com.ticket.sale.core.events.infra.persistence.jpa.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "spot_reservations",
        uniqueConstraints = { @UniqueConstraint(name = "uk_spot_reservation_spot", columnNames = "spot_id")} )
public class SpotReservationEntity {

    @Id
    @Column(name = "spot_id", length = 50)
    private String spotId;

    @Column(name = "customer_id", length = 50, nullable = false)
    private String customerId;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    public SpotReservationEntity() {}

    public SpotReservationEntity(String spotId, String customerId, LocalDateTime reservationDate) {
        this.spotId = spotId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
    }

    public String getSpotId() { return spotId; }

    public String getCustomerId() { return customerId; }

    public LocalDateTime getReservationDate() { return reservationDate; }

    public void setSpotId(String spotId) { this.spotId = spotId; }

    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }

}
