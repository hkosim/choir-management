package com.hk.personal.choir_management.service;

import com.hk.personal.choir_management.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.dto.attendance.MemberAttendanceDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendanceService {
    List<MemberAttendanceDto> findAllGroupedByMembers();
}
