package com.hk.personal.choir_management.dto.attendance;

import java.util.List;

public record MemberAttendanceDto(
        String memberUsername,
        String memberName,
        String voicePart,
        List<AttendanceDto> attendances
) {}