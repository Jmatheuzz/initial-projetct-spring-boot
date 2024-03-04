package com.indicafilmesai.backend.Dtos;

import com.indicafilmesai.backend.Models.Role;

public record RecoveryUpdateUser(
        Long id,
        String email,
        Role role
) {
}
