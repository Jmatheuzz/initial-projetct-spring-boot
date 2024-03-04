package com.indicafilmesai.backend.Dtos;

import com.indicafilmesai.backend.Models.Role;

public record ValidationEmail(

        String email,
        String code

) {
}
