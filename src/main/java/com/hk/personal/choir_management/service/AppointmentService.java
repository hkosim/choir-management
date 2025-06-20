package com.hk.personal.choir_management.service;

import com.hk.personal.choir_management.model.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.model.entity.Appointment;
import com.hk.personal.choir_management.model.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AppointmentService {
    Page<AppointmentAttendanceDto> findAppointmentsByUsernameAndDate(
            String username,
            LocalDate date,
            Pageable pageable);

    Appointment findAppointmentById(Long id);


    Appointment addAppointment(Appointment appointment);
    Appointment saveAppointment(Appointment appointment);

    AppointmentAttendanceDto saveAttendance(String username, Long id, AttendanceStatus attendanceStatus);
}
