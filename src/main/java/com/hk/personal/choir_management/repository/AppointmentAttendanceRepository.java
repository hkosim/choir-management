package com.hk.personal.choir_management.repository;

import com.hk.personal.choir_management.model.entity.Appointment;
import com.hk.personal.choir_management.model.entity.AppointmentAttendance;
import com.hk.personal.choir_management.model.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentAttendanceRepository extends JpaRepository<AppointmentAttendance, Long> {
    List<AppointmentAttendance> findByMemberUsername(String username);

    // Fetch all future rehearsals with attendance
    Page<AppointmentAttendance> findByMemberUsernameAndAppointmentDateAfter(
            String username,
            LocalDate date,
            Pageable pageable);

    Optional<AppointmentAttendance> findByMemberAndAppointment(Member member, Appointment appointment);
}
