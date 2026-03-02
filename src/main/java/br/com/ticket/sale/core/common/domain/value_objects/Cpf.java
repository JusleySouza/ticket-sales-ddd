package br.com.ticket.sale.core.common.domain.value_objects;

import br.com.ticket.sale.core.common.domain.exception.InvalidCpfError;

public class Cpf extends ValueObject<String> {

    public Cpf(String value) {
        super(clean(value));
        validate();
    }

    private static String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\D", "");
    }

    private void validate() {

        if (getValue().length() != 11) {
            throw new InvalidCpfError(
                    "CPF must have 11 digits, but has " + getValue().length() + " digits"
            );
        }

        if (getValue().matches("(\\d)\\1{10}")) {
            throw new InvalidCpfError("CPF must have at least two different digits");
        }

        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(getValue().charAt(i)) * (10 - i);
        }

        int firstDigit = 11 - (sum % 11);
        if (firstDigit > 9) {
            firstDigit = 0;
        }

        sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(getValue().charAt(i)) * (11 - i);
        }

        int secondDigit = 11 - (sum % 11);
        if (secondDigit > 9) {
            secondDigit = 0;
        }

        if (firstDigit != Character.getNumericValue(getValue().charAt(9)) ||
                secondDigit != Character.getNumericValue(getValue().charAt(10))) {

            throw new InvalidCpfError("CPF is invalid");
        }
    }
}
