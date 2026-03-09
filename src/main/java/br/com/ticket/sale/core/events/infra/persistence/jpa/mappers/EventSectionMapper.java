package br.com.ticket.sale.core.events.infra.persistence.jpa.mappers;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSection;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionConstructorProps;
import br.com.ticket.sale.core.events.domain.entities.event.section.EventSectionId;
import br.com.ticket.sale.core.events.domain.entities.event.spot.EventSpot;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.EventSectionJpaEntity;
import br.com.ticket.sale.core.events.infra.persistence.jpa.entities.EventSpotJpaEntity;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

public class EventSectionMapper {

    public static EventSectionJpaEntity fromDomain(EventSection section) {

        List<EventSpotJpaEntity> spots =
                section.getSpots()
                        .stream()
                        .map(EventSpotMapper::fromDomain)
                        .collect(Collectors.toList());

        EventSectionJpaEntity entity = new EventSectionJpaEntity();

        entity.setId(section.getId().getValue());
        entity.setName(section.getName().getValue());
        entity.setDescription(section.getDescription());
        entity.setPublished(section.isPublished());
        entity.setTotalSpots(section.getTotalSpots());
        entity.setTotalSpotsReserved(section.getTotalSpotsReserved());
        entity.setPrice(section.getPrice());
        entity.setSpots(spots);

        return entity;
    }

    public static EventSection toDomain(EventSectionJpaEntity entity) {

        Set<EventSpot> spots =
                entity.getSpots()
                        .stream()
                        .map(EventSpotMapper::toDomain)
                        .collect(Collectors.toSet());

        EventSectionConstructorProps props =
                new EventSectionConstructorProps(
                        new EventSectionId(entity.getId()),
                        new Name(entity.getName()),
                        entity.getDescription(),
                        entity.isPublished(),
                        entity.getTotalSpots(),
                        entity.getTotalSpotsReserved(),
                        entity.getPrice(),
                        spots
                );

        return new EventSection(props);
    }
}
