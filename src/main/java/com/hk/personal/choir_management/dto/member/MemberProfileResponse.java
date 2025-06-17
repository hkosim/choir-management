package com.hk.personal.choir_management.dto.member;

import java.time.LocalDate;

public record MemberProfileResponse(
        String username,
        String name,
        String email,
        String phone,
        String voicePart,
        LocalDate joinDate
) {
}
