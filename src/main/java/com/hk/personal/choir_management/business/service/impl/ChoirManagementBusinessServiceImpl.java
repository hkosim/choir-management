package com.hk.personal.choir_management.business.service.impl;

import com.hk.personal.choir_management.business.service.ChoirManagementBusinessService;
import com.hk.personal.choir_management.model.dto.AppointmentView;
import com.hk.personal.choir_management.model.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.model.dto.appointment.AppointmentAttendanceRequestDto;
import com.hk.personal.choir_management.model.dto.attendance.MemberAttendanceDto;
import com.hk.personal.choir_management.model.dto.member.*;
import com.hk.personal.choir_management.model.entity.*;
import com.hk.personal.choir_management.model.enums.AppointmentType;
import com.hk.personal.choir_management.service.AppointmentService;
import com.hk.personal.choir_management.service.AttendanceService;
import com.hk.personal.choir_management.service.MemberService;
import com.hk.personal.choir_management.service.SongService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Service
public class ChoirManagementBusinessServiceImpl implements ChoirManagementBusinessService {

    private final MemberService memberService;
    private final AttendanceService attendanceService;
    private final SongService songService;
    private final AppointmentService appointmentService;

    public ChoirManagementBusinessServiceImpl(MemberService memberService, AttendanceService attendanceService, SongService songService, AppointmentService appointmentService) {
        this.memberService = memberService;
        this.attendanceService = attendanceService;
        this.songService = songService;
        this.appointmentService = appointmentService;
    }

    // REGISTER
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        Member member = memberService.register(registerRequest);
        return new RegisterResponse(member.getUsername());
    }

    // LOGIN
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberService.login(loginRequest);
        // Generate token
        String combined = member.getUsername() + ":" + loginRequest.password();
        String token = Base64.getEncoder()
                .encodeToString(combined.getBytes(StandardCharsets.UTF_8));

        return new LoginResponse(
                member.getUsername(),
                member.getAuthorities().stream().map(
                        Authority::getAuthority
                ).toList(),
                token);
    }

    @Override
    @PreAuthorize("#username == authentication.getName() or hasRole('ADMIN')")
    public List<String> getRoles(String username) {
        return memberService.findRoles(username);
    }

    @Override
    public MemberProfileDto getMemberProfile(String username) {
        Member member = memberService.findByUsername(username);

        return new MemberProfileDto(
                member.getUsername(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getVoicePart(),
                member.getJoinDate()
        );
    }

    @Override
    public Page<Member> getAllMembers(Pageable pageable) {
        return memberService.findAll(pageable);
    }

    @Override
    public List<MemberAttendanceDto> getAllAttendances() {
        return attendanceService.findAllGroupedByMembers();
    }


    @Override
    public Page<Song> getSongs(Pageable pageable) {
        return songService.findAll(pageable);
    }

    @Override
    public Page<AppointmentAttendanceDto> getMemberAttendances(String username, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("appointment.date").ascending()
        );
        return appointmentService.findAppointmentsByUsernameAndDate(
                username,
                LocalDate.now(),
                sortedPageable
        );
    }

    @Override
    public Appointment getAppointment(Long id) {
        return appointmentService.findAppointmentById(id);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        if (appointment.getId() == 0L) {
            appointment.setId(null);
            return appointmentService.addAppointment(appointment);

        }
        return appointmentService.saveAppointment(appointment);
    }

    @Override
    public AppointmentAttendanceDto updateAttendance(AppointmentAttendanceRequestDto appointmentAttendanceRequestDto) {
        return appointmentService.saveAttendance(
                appointmentAttendanceRequestDto.username(),
                appointmentAttendanceRequestDto.id(),
                appointmentAttendanceRequestDto.attendanceStatus()
        );
    }

}
