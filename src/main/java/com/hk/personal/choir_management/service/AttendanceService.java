package com.hk.personal.choir_management.service;

import com.hk.personal.choir_management.model.dto.attendance.MemberAttendanceDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendanceService {
    List<MemberAttendanceDto> findAllGroupedByMembers();
}
