package br.com.ticket.sale.core.events.infra.persistence.jpa.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "event_sections")
public class EventSectionJpaEntity {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "published", nullable = false)
    private boolean isPublished;

    @Column(name = "total_spots", nullable = false)
    private int totalSpots;

    @Column(name = "total_spots_reserved", nullable = false)
    private int totalSpotsReserved;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventJpaEntity event;

    @OneToMany(
            mappedBy = "section",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<EventSpotJpaEntity> spots;

    public EventSectionJpaEntity() {}

    public EventSectionJpaEntity(
            String id,
            String name,
            String description,
            boolean isPublished,
            int totalSpots,
            int totalSpotsReserved,
            BigDecimal price,
            EventJpaEntity event,
            List<EventSpotJpaEntity> spots
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPublished = isPublished;
        this.totalSpots = totalSpots;
        this.totalSpotsReserved = totalSpotsReserved;
        this.price = price;
        this.event = event;
        this.spots = spots;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public int getTotalSpotsReserved() {
        return totalSpotsReserved;
    }

    public void setTotalSpotsReserved(int totalSpotsReserved) {
        this.totalSpotsReserved = totalSpotsReserved;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public EventJpaEntity getEvent() {
        return event;
    }

    public void setEvent(EventJpaEntity event) {
        this.event = event;
    }

    public List<EventSpotJpaEntity> getSpots() {
        return spots;
    }

    public void setSpots(List<EventSpotJpaEntity> spots) {
        this.spots = spots;
    }
}
