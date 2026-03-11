package br.com.ticket.sale.core.events.domain.entities.event.spot;

import br.com.ticket.sale.core.common.domain.Entity;

import java.util.Map;

//Os spots são as vagas do evento, eles tem um ciclo de vida próprio,
//e podem ser modificados sem afetar o evento.
public class EventSpot extends Entity<EventSpotId> {

    private String location;
    private boolean isReserved;
    private boolean isPublished;

    public EventSpot(EventSpotConstructorProps props) {

        this.id = props.id() == null
                ? new EventSpotId()
                : props.id();

        this.location = props.location();
        this.isReserved = props.isReserved();
        this.isPublished = props.isPublished();
    }

    public static EventSpot create(String location) {
        return new EventSpot(
                new EventSpotConstructorProps(
                        new EventSpotId(),
                        location,
                        false,
                        false
                )
        );
    }

    public void markAsReserved() {
        this.isReserved = true;
    }

    public void changeLocation(String location) {
        this.location = location;
    }

    public void publish() {
        this.isPublished = true;
    }

    public void unPublish() {
        this.isPublished = false;
    }

    public void reserve() {
        this.isReserved = true;
    }

    public void unReserve() {
        this.isReserved = false;
    }

    public String getLocation() {
        return location;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public boolean isPublished() {
        return isPublished;
    }

    @Override
    public Map<String, Object> toJSON() {
        return Map.of(
                "id", id.getValue(),
                "location", location,
                "reserved", isReserved,
                "is_published", isPublished
        );
    }
}
