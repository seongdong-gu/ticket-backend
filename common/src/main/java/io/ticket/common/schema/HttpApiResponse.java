package io.ticket.common.schema;

public record HttpApiResponse <T>(
    int status,
    String message,
    T data
) {
}
