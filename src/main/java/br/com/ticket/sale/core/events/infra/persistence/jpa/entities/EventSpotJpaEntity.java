package br.com.ticket.sale.core.events.infra.persistence.jpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name="event_spots")
public class EventSpotJpaEntity {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @Column(name = "reserved", nullable = false)
    private boolean isReserved;

    @Column(name = "published", nullable = false)
    private boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private EventSectionJpaEntity section;

    public EventSpotJpaEntity() {}

    public String getId() { return id; }
    public String getLocation() { return location; }
    public boolean isReserved() { return isReserved; }
    public boolean isPublished() { return isPublished; }

    public void setId(String id) { this.id = id; }
    public void setLocation(String location) { this.location = location; }
    public void setReserved(boolean reserved) { this.isReserved = reserved; }
    public void setPublished(boolean published) { this.isPublished = published; }

    public EventSectionJpaEntity getSection() { return section; }
    public void setSection(EventSectionJpaEntity section) { this.section = section; }

}
