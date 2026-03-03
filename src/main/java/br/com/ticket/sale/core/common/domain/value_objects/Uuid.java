package br.com.ticket.sale.core.common.domain.value_objects;

import br.com.ticket.sale.core.common.domain.exception.InvalidUuidError;

import java.util.UUID;

public class Uuid extends ValueObject<String> {

    public Uuid() {
        super(UUID.randomUUID().toString());
        validate();
    }

    public Uuid(String value) {
        super(value);
        validate();
    }

    private void validate() {
        try {
            UUID.fromString(this.value);
        } catch (IllegalArgumentException e) {
            throw new InvalidUuidError(getValue());
        }
    }
}
