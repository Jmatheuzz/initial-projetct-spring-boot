package com.indicafilmesai.backend.Dtos;

import com.indicafilmesai.backend.Models.Role;

public record SignUpDto(

        String email,
        String password

) {
}
