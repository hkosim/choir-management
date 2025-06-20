package com.hk.personal.choir_management.model.dto.member;

import java.time.LocalDate;

public record RegisterRequest(
        String username,
        String rawPassword,
        String name,
        String email,
        String phone,
        String voicePart,
        LocalDate joinDate,
        String role
) {
}
