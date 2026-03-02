package br.com.ticket.sale.core.common.domain.exception;

public class InvalidCpfError extends RuntimeException {

    public InvalidCpfError(String message) {
        super(message);
    }
}
