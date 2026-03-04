package br.com.ticket.sale.core.events.domain.entities.event.spot;

public record EventSpotConstructorProps(
        EventSpotId id,
        String location,
        boolean isReserved,
        boolean isPublished
) {}
