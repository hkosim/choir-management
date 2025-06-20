package com.hk.personal.choir_management.model.dto.member;

import java.time.LocalDate;

public record MemberProfileDto(
        String username,
        String name,
        String email,
        String phone,
        String voicePart,
        LocalDate joinDate
) {
}
