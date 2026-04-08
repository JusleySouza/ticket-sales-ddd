package br.com.ticket.sale.core.events.domain.entities.event;

import br.com.ticket.sale.core.common.domain.AggregateRoot;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.event.AddSectionCommand;
import br.com.ticket.sale.core.events.application.commands.event.CreateEventCommand;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.application.commands.event.EventSectionCreateCommand;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpotId;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;

import java.time.LocalDateTime;
import java.util.*;

//O evento é o agregado raiz, ele tem uma coleção de sessões,
//e cada sessão tem uma coleção de vagas.
public class Event extends AggregateRoot<EventId> {

    private Name name;
    private String description;
    private LocalDateTime date;
    private boolean isPublished;
    private int totalSpots;
    private int totalSpotsReserved;
    private PartnerId partnerId;
    private final Set<EventSection> sections;

    public Event(EventConstructorProps props) {

        this.id = props.id() == null
                ? new EventId()
                : props.id();

        this.name = props.name();
        this.description = props.description();
        this.date = props.date();
        this.isPublished = props.isPublished();
        this.totalSpots = props.totalSpots();
        this.totalSpotsReserved = props.totalSpotsReserved();
        this.partnerId = props.partnerId();

        this.sections = props.sections() != null
                ? new HashSet<>(props.sections())
                : new HashSet<>();
    }

    public static Event create(CreateEventCommand command) {
        return new Event(
                new EventConstructorProps(
                        new EventId(),
                        command.name(),
                        command.description(),
                        command.date(),
                        false,
                        0,
                        0,
                        command.partnerId(),
                        new HashSet<>()
                )
        );
    }

    public void changeName(Name name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeDate(LocalDateTime date) {
        this.date = date;
    }

    public void publishAll() {
        publish();
        sections.forEach(EventSection::publishAll);
    }

    public void publish() {
        this.isPublished = true;
    }

    public void unPublish() {
        this.isPublished = false;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public int getTotalSpotsReserved() {
        return totalSpotsReserved;
    }

    public void addSection(AddSectionCommand command) {
        EventSectionCreateCommand createCommand =
                new EventSectionCreateCommand(
                        command.name(),
                        command.description(),
                        command.totalSpots(),
                        command.price()
                );
        EventSection section = EventSection.create(createCommand);
        this.sections.add(section);
        this.totalSpots += section.getTotalSpots();
    }

    public boolean allowReserveSpot(EventSectionId sectionId, EventSpotId spotId ) {
        if (!isPublished()) {
            return false;
        }

        EventSection section = sections.stream()
                .filter(s -> s.getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Section not found"));

        return section.allowReserveSpot(spotId);
    }

    public void markSpotAsReserved( EventSectionId sectionId, EventSpotId spotId ) {
        EventSection section = this.sections
                .stream()
                .filter(s -> s.getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Section not found"));

        section.markSpotAsReserved(spotId);
    }

    public Name getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() { return date; }

    public PartnerId getPartnerId() { return partnerId; }

    public boolean isPublished() {
        return isPublished;
    }

    public Set<EventSection> getSections() {
        return Collections.unmodifiableSet(sections);
    }

    @Override
    public Map<String, Object> toJSON() {

        List<Map<String, Object>> sectionsJson =
                sections.stream()
                        .map(EventSection::toJSON)
                        .toList();

        return Map.of(
                "id", id.getValue(),
                "name", name,
                "description", description,
                "date", date,
                "is_published", isPublished,
                "total_spots", totalSpots,
                "total_spots_reserved", totalSpotsReserved,
                "partner_id", partnerId.getValue(),
                "sections", sectionsJson
        );
    }
}
