package io.ticket.common.schema;

public record HttpApiResponse <T>(
    int status,
    String message,
    T data
) {

    public static <T> HttpApiResponse<T> ok(final T token) {
        return new HttpApiResponse<>(200, "OK", token);
    }
}
