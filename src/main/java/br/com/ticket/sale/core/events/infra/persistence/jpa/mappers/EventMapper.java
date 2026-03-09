package br.com.ticket.sale.core.events.infra.persistence.jpa.mappers;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.entities.event.EventConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.event.EventId;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpot;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.EventJpaEntity;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.EventSectionJpaEntity;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.EventSpotJpaEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventJpaEntity fromDomain(Event event) {

        EventJpaEntity eventEntity = new EventJpaEntity();

        eventEntity.setId(event.getId().getValue());
        eventEntity.setName(event.getName().getValue());
        eventEntity.setDescription(event.getDescription());
        eventEntity.setDate(event.getDate());
        eventEntity.setPublished(event.isPublished());
        eventEntity.setTotalSpots(event.getTotalSpots());
        eventEntity.setTotalSpotsReserved(event.getTotalSpotsReserved());
        eventEntity.setPartnerId(event.getPartnerId().getValue());

        List<EventSectionJpaEntity> sections =
                event.getSections()
                        .stream()
                        .map(section -> {

                            EventSectionJpaEntity sectionEntity =
                                    EventSectionMapper.fromDomain(section);

                            sectionEntity.setEvent(eventEntity);

                            List<EventSpotJpaEntity> spots =
                                    sectionEntity.getSpots()
                                            .stream()
                                            .peek(spot -> spot.setSection(sectionEntity))
                                            .collect(Collectors.toList());

                            sectionEntity.setSpots(spots);

                            return sectionEntity;

                        })
                        .collect(Collectors.toList());

        eventEntity.setSections(sections);

        return eventEntity;
    }

    public static Event toDomain(EventJpaEntity entity) {

        Set<EventSection> sections =
                entity.getSections()
                        .stream()
                        .map(sectionEntity -> {

                            Set<EventSpot> spots =
                                    sectionEntity.getSpots()
                                            .stream()
                                            .map(EventSpotMapper::toDomain)
                                            .collect(Collectors.toSet());

                            EventSectionConstructorProps props =
                                    new EventSectionConstructorProps(
                                            new EventSectionId(sectionEntity.getId()),
                                            new Name(sectionEntity.getName()),
                                            sectionEntity.getDescription(),
                                            sectionEntity.isPublished(),
                                            sectionEntity.getTotalSpots(),
                                            sectionEntity.getTotalSpotsReserved(),
                                            sectionEntity.getPrice(),
                                            spots
                                    );

                            return new EventSection(props);
                        })
                        .collect(Collectors.toSet());

        EventConstructorProps props =
                new EventConstructorProps(
                        new EventId(entity.getId()),
                        new Name(entity.getName()),
                        entity.getDescription(),
                        entity.getDate(),
                        entity.isPublished(),
                        entity.getTotalSpots(),
                        entity.getTotalSpotsReserved(),
                        new PartnerId(entity.getPartnerId()),
                        sections
                );

        return new Event(props);
    }
}
