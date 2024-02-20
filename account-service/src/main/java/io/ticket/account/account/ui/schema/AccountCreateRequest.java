package io.ticket.account.account.ui.schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountCreateRequest(
    @NotBlank(message = "Username is required")
        @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
        String username,
    @NotBlank(message = "Password is required")
        @Size(min = 15, max = 30, message = "Password must be between 15 and 30 characters")
        String password) {}
