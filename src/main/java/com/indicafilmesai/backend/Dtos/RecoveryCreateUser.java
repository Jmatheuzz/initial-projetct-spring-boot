package com.indicafilmesai.backend.Dtos;

import com.indicafilmesai.backend.Models.Role;

public record RecoveryCreateUser(
        Long id,
        String email,
        Role role
) {
}
