package br.com.ticket.sale.core.events.domain.entities.partner;

import br.com.ticket.sale.core.common.domain.AggregateRoot;
import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.domain.entities.event.CreateEventCommand;
import br.com.ticket.sale.core.events.domain.entities.event.Event;

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
        return new Partner(
                new PartnerConstructorProps(
                        new PartnerId(),
                        name
                )
        );
    }

    public Event initEvent(InitEventCommand command) {
        CreateEventCommand createCommand = new CreateEventCommand(
                command.name(),
                command.description(),
                command.date(),
                this.id
        );

        return Event.create(createCommand);
    }

    public void changeName(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    @Override
    public Map<String, Object> toJSON() {
        return Map.of(
                "id", id.getValue(),
                "name", name
        );
    }
}
