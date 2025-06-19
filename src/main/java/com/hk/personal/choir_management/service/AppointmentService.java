package com.hk.personal.choir_management.service;

import com.hk.personal.choir_management.dto.AppointmentView;
import com.hk.personal.choir_management.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.entity.Performance;
import com.hk.personal.choir_management.entity.Rehearsal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AppointmentService {
    Page<AppointmentAttendanceDto> findAppointmentsByUsernameAndDate(
            String username,
            LocalDate date,
            Pageable pageable);

    AppointmentAttendanceDto saveAttendance(
            String username,
            Long id,
            String type,
            Boolean present
    );

    AppointmentView findAppointmentByType(String type, Long id);

    Rehearsal saveRehearsal(Rehearsal rehearsal);
    Performance savePerformance(Performance performance);
}
