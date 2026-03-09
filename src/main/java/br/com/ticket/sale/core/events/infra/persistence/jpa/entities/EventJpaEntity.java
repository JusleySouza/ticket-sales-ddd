package br.com.ticket.sale.core.events.infra.persistence.jpa.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="events")
public class EventJpaEntity {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "published", nullable = false)
    private boolean isPublished;

    @Column(name = "total_spots", nullable = false)
    private int totalSpots;

    @Column(name = "total_spots_reserved", nullable = false)
    private int totalSpotsReserved;

    @Column(name = "partner_id", nullable = false)
    private String partnerId;

    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<EventSectionJpaEntity> sections;

    public EventJpaEntity() {
    }

    public EventJpaEntity(
            String id,
            String name,
            String description,
            LocalDateTime date,
            boolean isPublished,
            int totalSpots,
            int totalSpotsReserved,
            String partnerId,
            List<EventSectionJpaEntity> sections
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.isPublished = isPublished;
        this.totalSpots = totalSpots;
        this.totalSpotsReserved = totalSpotsReserved;
        this.partnerId = partnerId;
        this.sections = sections;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public int getTotalSpotsReserved() {
        return totalSpotsReserved;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public List<EventSectionJpaEntity> getSections() {
        return sections;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public void setTotalSpotsReserved(int totalSpotsReserved) {
        this.totalSpotsReserved = totalSpotsReserved;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public void setSections(List<EventSectionJpaEntity> sections) {
        this.sections = sections;
    }
}
