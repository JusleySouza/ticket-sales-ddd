package br.com.ticket.sale.core.events.interfaces.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleRuntime(RuntimeException ex) {

        return ApiResponse.error(
                new ApiError("BUSINESS_ERROR", ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleGeneric(Exception ex) {

        return ApiResponse.error(
                new ApiError("INTERNAL_ERROR", "Unexpected error")
        );
    }
}
