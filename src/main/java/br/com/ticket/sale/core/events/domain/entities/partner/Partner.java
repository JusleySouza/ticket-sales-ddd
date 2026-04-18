package br.com.ticket.sale.core.events.domain.entities.partner;

import br.com.ticket.sale.core.common.domain.entity.AggregateRoot;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.event.CreateEventCommand;
import br.com.ticket.sale.core.events.application.commands.partner.InitEventCommand;
import br.com.ticket.sale.core.events.domain.entities.event.Event;
import br.com.ticket.sale.core.events.domain.events.EventCreated;
import br.com.ticket.sale.core.events.domain.events.PartnerChangedNameEvent;
import br.com.ticket.sale.core.events.domain.events.PartnerCreatedEvent;

import java.util.Map;

//Parceiro é o responsável por criar eventos,
//ele pode ter vários eventos associados a ele,
//mas um evento só pode ter um parceiro associado a ele.
public class Partner extends AggregateRoot<PartnerId> {

    private Name name;

    public Partner(PartnerConstructorProps props) {

        this.id = props.id() == null
                ? new PartnerId()
                : props.id();

        this.name = props.name();
    }

    public static Partner create(Name name) {
        Partner partner = new Partner(
                new PartnerConstructorProps(
                        new PartnerId(),
                        name
                )
        );
        partner.addEvent(new PartnerCreatedEvent(partner.getId()));
        return partner;
    }

    public Event initEvent(InitEventCommand command) {
        CreateEventCommand createCommand = new CreateEventCommand(
                command.name(),
                command.description(),
                command.date(),
                this.id
        );
        Event event = Event.create(createCommand);
        this.addEvent(new EventCreated(this.id, event.getId()));
        return event;
    }

    public void changeName(Name name) {
        this.name = name;
        this.addEvent(new PartnerChangedNameEvent(this.getId(), name));
    }

    public Name getName() {
        return name;
    }

    @Override
    public Map<String, Object> toJSON() {
        return Map.of(
                "id", id.getValue(),
                "name", name.getValue()
        );
    }
}
