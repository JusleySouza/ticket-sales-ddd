package br.com.ticket.sale.core.common.domain.value_objects;

public class Name extends ValueObject<String> {

    public Name(String name) {
        super(name);
        validate();
    }

    public boolean isValid() {
        return getValue() != null && getValue().length() >= 3;
    }

    private void validate() {
        if (!isValid()) {
            throw new IllegalArgumentException("Name must have at least 3 characters");
        }
    }
}
