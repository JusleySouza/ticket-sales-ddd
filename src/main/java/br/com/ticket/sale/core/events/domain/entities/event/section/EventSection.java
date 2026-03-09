package br.com.ticket.sale.core.events.domain.entities.event.section;

import br.com.ticket.sale.core.common.domain.Entity;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpot;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//A sessão é uma parte do evento, ela tem uma coleção de vagas.
//A sessão é uma entidade, pois tem um ciclo de vida próprio,
//e pode ser modificada sem afetar o evento.
public class EventSection extends Entity<EventSectionId> {

    private Name name;
    private String description;
    private boolean isPublished;
    private int totalSpots;
    private int totalSpotsReserved;
    private BigDecimal price;
    private Set<EventSpot> spots;

    public EventSection(EventSectionConstructorProps props) {

        this.id = props.id() == null
                ? new EventSectionId()
                : props.id();

        this.name = props.name();
        this.description = props.description();
        this.isPublished = props.isPublished();
        this.totalSpots = props.totalSpots();
        this.totalSpotsReserved = props.totalSpotsReserved();
        this.price = props.price();
        this.spots = props.spots() == null
                ? new HashSet<>()
                : props.spots();
    }

    public static EventSection create(EventSectionCreateCommand command) {

        EventSection section = new EventSection(
                new EventSectionConstructorProps(
                        new EventSectionId(),
                        command.name(),
                        command.description(),
                        false,
                        command.totalSpots(),
                        0,
                        command.price(),
                        new HashSet<>()
                )
        );

        section.initSpots();
        return section;
    }

    private void initSpots() {
        for (int i = 1; i <= this.totalSpots; i++) {
            this.spots.add(EventSpot.create("SPOT-" + i));
        }
    }

    public void changeName(Name name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }

    public void publishAll() {
        publish();
        this.spots.forEach(EventSpot::publish);
    }

    public void publish() {
        this.isPublished = true;
    }

    public void unPublish() {
        this.isPublished = false;
    }

    public Name getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public BigDecimal getPrice() {
        return price;
    }

    public Set<EventSpot> getSpots() {
        return Collections.unmodifiableSet(spots);
    }

    @Override
    public Map<String, Object> toJSON() {
        return Map.of(
                "id", id.getValue(),
                "name", name,
                "description", description,
                "is_published", isPublished,
                "total_spots", totalSpots,
                "total_spots_reserved", totalSpotsReserved,
                "price", price,
                "spots", spots.stream()
                        .map(EventSpot::toJSON)
                        .toList()
        );
    }
}
