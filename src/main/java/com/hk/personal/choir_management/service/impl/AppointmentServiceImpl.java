package com.hk.personal.choir_management.service.impl;

import com.hk.personal.choir_management.model.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.model.entity.*;
import com.hk.personal.choir_management.model.enums.AttendanceStatus;
import com.hk.personal.choir_management.repository.*;
import com.hk.personal.choir_management.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final AppointmentAttendanceRepository appointmentAttendanceRepository;
    private final MemberRepository memberRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentAttendanceRepository appointmentAttendanceRepository, MemberRepository memberRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentAttendanceRepository = appointmentAttendanceRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Returns all appointments with attendance status for a given user.
     *
     * @param username the user's username
     * @param date     the requested date from
     * @param pageable the requested page size
     * @return the appointments for this member
     */
    @Override
    public Page<AppointmentAttendanceDto> findAppointmentsByUsernameAndDate(String username, LocalDate date, Pageable pageable) {
        LocalDate today = LocalDate.now();
        // Returns all the deleted items if logged in is admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        Page<AppointmentAttendance> appointmentAttendances = appointmentAttendanceRepository.
                findByMemberUsernameAndAppointmentDateAfter(
                        username, today.minusDays(1), pageable
                );
//        boolean isAdmin = authentication.getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
//        if (isAdmin) {
//            // Admin sees everything (including soft-deleted appointments)
//            appointmentAttendances = appointmentAttendanceRepository.
//                    findByMemberUsernameAndAppointmentDateAfter(
//                            username, today.minusDays(1), pageable
//                    );
//        } else {
//            // Regular users only see appointments that are NOT soft-deleted
//            appointmentAttendances = appointmentAttendanceRepository.findByMemberUsernameAndAppointmentDateAfterAndAppointmentDeletedAtIsNull(
//                    username, today.minusDays(1), pageable
//            );
//        }

        return appointmentAttendances.map(appointmentAttendance ->
                new AppointmentAttendanceDto(
                        appointmentAttendance.getAppointment(),
                        appointmentAttendance.getAttendanceStatus()
                ));
    }

    /**
     * Returns all appointments with attendance status for a given user.
     *
     * @param id of the appointment.
     * @return the appointments for this member.
     */
    @Override
    public Appointment findAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found: " + id));
    }

    /**
     * Save a new appointment.
     *
     * @param appointment updated object.
     * @return the new rehearsal onject.
     */
    @Override
    public Appointment addAppointment(Appointment appointment) {
        Appointment newAppointment = appointmentRepository.save(appointment);

        // Save attendances
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            AppointmentAttendance appointmentAttendance = new AppointmentAttendance(
                    member,
                    newAppointment,
                    AttendanceStatus.UNKNOWN
            );
            appointmentAttendanceRepository.save(appointmentAttendance);
        }
        return newAppointment;
    }

    /**
     * Save an existing appointment.
     *
     * @param appointment updated object.
     * @return the new rehearsal onject.
     */
    @Override
    public Appointment saveAppointment(Appointment appointment) {
        Appointment _appointment = appointmentRepository.
                findById(appointment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found:" + appointment.getId()));

        // Save the details
        _appointment.setTitle(appointment.getTitle());
        _appointment.setDescription(appointment.getDescription());
        _appointment.setDate(appointment.getDate());
        _appointment.setTime(appointment.getTime());
        _appointment.setLocation(appointment.getLocation());
        _appointment.setAppointmentType(appointment.getAppointmentType());

        return appointmentRepository.save(_appointment);
    }


    /**
     * Performs a soft-delete.
     *
     * @param id of appointment that will be deleted.
     * @return deleted object.
     */
    @Override
    public Appointment deleteAppointment(Long id) {
        Appointment _appointment = appointmentRepository.
                findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found:" + id));

        // Save the details
        _appointment.setDeletedAt(LocalDateTime.now());

        return appointmentRepository.save(_appointment);
    }

    /**
     * Performs restores an appointment.
     *
     * @param id of appointment that was deleted.
     * @return restored appointment.
     */
    @Override
    public Appointment restoreAppointment(Long id) {
        Appointment _appointment = appointmentRepository.
                findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found:" + id));

        // Save the details
        _appointment.setDeletedAt(null);

        return appointmentRepository.save(_appointment);
    }

    /**
     * Save an attendance
     *
     * @param username         the user's username
     * @param id               the appointment id
     * @param attendanceStatus the new attendance status
     * @return the appointment attendance.
     */
    @Override
    public AppointmentAttendanceDto saveAttendance(String username, Long id, AttendanceStatus attendanceStatus) {
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("Member not found: " + username));
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found: " + id));

        AppointmentAttendance appointmentAttendance = appointmentAttendanceRepository
                .findByMemberAndAppointment(
                        member,
                        appointment
                ).orElse(new AppointmentAttendance());

        appointmentAttendance.setMember(member);
        appointmentAttendance.setAppointment(appointment);
        appointmentAttendance.setAttendanceStatus(attendanceStatus);

        appointmentAttendanceRepository.save(appointmentAttendance);

        return new AppointmentAttendanceDto(
                appointment,
                attendanceStatus
        );
    }
}
