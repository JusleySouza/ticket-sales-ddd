package br.com.ticket.sale.core.common.domain.value_objects;

public class Name extends ValueObject<String> {

    public Name(String name) {
        super(name);
        validate();
    }

    private void validate() {
        if (this.value == null || this.value.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (this.value.length() < 2) {
            throw new IllegalArgumentException("Name must have at least 2 characters");
        }
    }
}
