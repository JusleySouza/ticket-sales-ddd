package br.com.ticket.sale.core.events.interfaces.api;

public record ApiResponse<T>(
        T data,
        ApiError error,
        Object meta
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null, null);
    }

    public static <T> ApiResponse<T> error(ApiError error) {
        return new ApiResponse<>(null, error, null);
    }

}
