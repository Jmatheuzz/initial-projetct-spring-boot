package com.indicafilmesai.backend.Dtos;

import com.indicafilmesai.backend.Models.Role;

public record CreateUserDto(

        String email,
        String password,
        Role role

) {
}
