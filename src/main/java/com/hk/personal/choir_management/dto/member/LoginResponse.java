package com.hk.personal.choir_management.dto.member;

import java.util.List;

public record LoginResponse(
        String username,
        List<String> roles,
        String token
) {
}
