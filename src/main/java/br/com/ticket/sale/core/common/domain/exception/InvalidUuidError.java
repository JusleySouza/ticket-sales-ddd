package br.com.ticket.sale.core.common.domain.exception;

public class InvalidUuidError extends RuntimeException {

    public InvalidUuidError(String invalidValue) {
        super("Value " + invalidValue + " must be a valid UUID");
    }

}
