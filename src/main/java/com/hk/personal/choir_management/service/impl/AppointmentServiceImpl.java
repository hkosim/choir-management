package com.hk.personal.choir_management.service.impl;

import com.hk.personal.choir_management.dto.AppointmentView;
import com.hk.personal.choir_management.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.dto.appointment.PerformanceDto;
import com.hk.personal.choir_management.dto.appointment.RehearsalDto;
import com.hk.personal.choir_management.entity.*;
import com.hk.personal.choir_management.repository.*;
import com.hk.personal.choir_management.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AppointmentServiceImpl implements AppointmentService {


    private final RehearsalRepository rehearsalRepository;
    private final PerformanceRepository performanceRepository;
    private final RehearsalAttendanceRepository rehearsalAttendanceRepository;
    private final PerformanceAttendanceRepository performanceAttendanceRepository;
    private final MemberRepository memberRepository;

    public AppointmentServiceImpl(RehearsalRepository rehearsalRepository, PerformanceRepository performanceRepository, RehearsalAttendanceRepository rehearsalAttendanceRepository, PerformanceAttendanceRepository performanceAttendanceRepository, MemberRepository memberRepository) {
        this.rehearsalRepository = rehearsalRepository;
        this.performanceRepository = performanceRepository;
        this.rehearsalAttendanceRepository = rehearsalAttendanceRepository;
        this.performanceAttendanceRepository = performanceAttendanceRepository;
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

        if (date == null) {
            today = LocalDate.now();
        }

        // Fetch all future rehearsals with attendance
        List<RehearsalAttendance> rehearsalAttendances = rehearsalAttendanceRepository
                .findByMemberUsernameAndRehearsalDateAfter(username, today.minusDays(1));

        List<AppointmentAttendanceDto> rehearsalDtos = rehearsalAttendances.stream()
                .map(ra -> {
                    Rehearsal r = ra.getRehearsal();
                    return new AppointmentAttendanceDto(
                            r.getId(),
                            "rehearsal",
                            r.getTitle(),
                            r.getDescription(),
                            r.getDate(),
                            r.getTime(),
                            r.getLocation(),
                            ra.isPresent()
                    );
                }).toList();


        // Fetch all future performances with attendance
        List<PerformanceAttendance> performanceAttendances = performanceAttendanceRepository
                .findByMemberUsernameAndPerformanceDateAfter(username, today.minusDays(1));

        List<AppointmentAttendanceDto> performanceDtos = performanceAttendances.stream()
                .map(pa -> {
                    Performance p = pa.getPerformance();
                    return new AppointmentAttendanceDto(
                            p.getId(),
                            "performance",
                            p.getTitle(),
                            p.getDescription(),
                            p.getDate(),
                            p.getTime(),
                            p.getLocation(),
                            pa.isPresent()
                    );
                }).toList();

        // Merge, sort, and paginate manually
        List<AppointmentAttendanceDto> merged = Stream.concat(rehearsalDtos.stream(), performanceDtos.stream())
                .sorted(
                        Comparator.comparing(AppointmentAttendanceDto::date)
                                .thenComparing(AppointmentAttendanceDto::time)
                )
                .toList();

        // Manual pagination logic
        int total = merged.size();
        int from = (int) pageable.getOffset();
        int to = Math.min(from + pageable.getPageSize(), total);
        List<AppointmentAttendanceDto> pagedList = from < to ? merged.subList(from, to) : List.of();

        return new PageImpl<>(pagedList, pageable, total);
    }

    /**
     * Returns all appointments with attendance status for a given user.
     *
     * @param type the user's username
     * @param id   of the performance or rehearsal
     * @return the appointments for this member
     */
    @Override
    public AppointmentView findAppointmentByType(String type, Long id) {
        if (type.equalsIgnoreCase("rehearsal")) {
            Rehearsal rehearsal = rehearsalRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Rehearsal not found: " + id));
            return new RehearsalDto(
                    rehearsal.getId(),
                    "rehearsal",
                    rehearsal.getTitle(),
                    rehearsal.getDescription(),
                    rehearsal.getDate(),
                    rehearsal.getTime(),
                    rehearsal.getLocation()
            );

        } else {
            Performance performance = performanceRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Performance not found: " + id));
            return new PerformanceDto(
                    performance.getId(),
                    "performance",
                    performance.getTitle(),
                    performance.getDescription(),
                    performance.getDate(),
                    performance.getTime(),
                    performance.getLocation(),
                    performance.getSongs()
            );
        }
    }

    /**
     * Save an attendance
     *
     * @param username the user's username
     * @param id       of the performance or rehearsal
     * @param type     (performance or rehearsal)
     * @return the appointment attendance.
     */
    @Override
    public AppointmentAttendanceDto saveAttendance(String username, Long id, String type, Boolean present) {
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("Member not found: " + username));

        if ("REHEARSAL".equalsIgnoreCase(type)) {
            Rehearsal rehearsal = rehearsalRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Rehearsal not found: " + id));

            // Check if already exists or make a new one
            RehearsalAttendance attendance = rehearsalAttendanceRepository
                    .findByMemberAndRehearsal(member, rehearsal)
                    .orElse(new RehearsalAttendance());

            attendance.setMember(member);
            attendance.setRehearsal(rehearsal);
            attendance.setPresent(present);

            rehearsalAttendanceRepository.save(attendance);

            return new AppointmentAttendanceDto(
                    rehearsal.getId(),
                    "rehearsal",
                    rehearsal.getTitle(),
                    rehearsal.getDescription(),
                    rehearsal.getDate(),
                    rehearsal.getTime(),
                    rehearsal.getLocation(),
                    present
            );
        } else if ("PERFORMANCE".equalsIgnoreCase(type)) {
            Performance performance = performanceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Performance not found: " + id));

            PerformanceAttendance attendance = performanceAttendanceRepository
                    .findByMemberAndPerformance(member, performance)
                    .orElse(new PerformanceAttendance());

            attendance.setMember(member);
            attendance.setPerformance(performance);
            attendance.setPresent(present);

            performanceAttendanceRepository.save(attendance);

            return new AppointmentAttendanceDto(
                    performance.getId(),
                    "performance",
                    performance.getTitle(),
                    performance.getDescription(),
                    performance.getDate(),
                    performance.getTime(),
                    performance.getLocation(),
                    present
            );
        } else {
            throw new IllegalArgumentException("Unknown appointment type: " + type);
        }
    }

    /**
     * Save updated rehearsal object.
     *
     * @param rehearsal updated object.
     * @return the updated rehearsal.
     */
    @Override
    public Rehearsal saveRehearsal(Rehearsal rehearsal) {
        return rehearsalRepository.save(rehearsal);
    }
}
