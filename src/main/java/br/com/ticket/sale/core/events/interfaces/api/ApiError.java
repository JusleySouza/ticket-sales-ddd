package br.com.ticket.sale.core.events.interfaces.api;

public class ApiError {

    private String code;
    private String message;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
