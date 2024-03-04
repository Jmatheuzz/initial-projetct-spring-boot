package com.indicafilmesai.backend.Dtos;

import com.indicafilmesai.backend.Models.Role;

public record SignUpStoreDto(

        String email,
        String password

) {
}
