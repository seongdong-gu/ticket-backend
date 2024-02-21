package io.ticket.auth.account.infrastructure.dto;

public record AuthenticateResponse(Long identity, boolean authenticated) {}
