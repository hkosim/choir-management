package com.hk.personal.choir_management.dto.attendance;

import java.time.LocalDate;

public record AttendanceDto(
        LocalDate rehearsalDate,
        boolean present) {
}