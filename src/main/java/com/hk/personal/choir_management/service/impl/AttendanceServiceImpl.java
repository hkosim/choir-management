package com.hk.personal.choir_management.service.impl;

import com.hk.personal.choir_management.model.dto.attendance.MemberAttendanceDto;
import com.hk.personal.choir_management.model.entity.AppointmentAttendance;
import com.hk.personal.choir_management.repository.AppointmentAttendanceRepository;
import com.hk.personal.choir_management.repository.MemberRepository;
import com.hk.personal.choir_management.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for retrieving attendance information.
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AppointmentAttendanceRepository appointmentAttendanceRepository;
    private final MemberRepository memberRepository;

    public AttendanceServiceImpl(AppointmentAttendanceRepository appointmentAttendanceRepository, MemberRepository memberRepository) {
        this.appointmentAttendanceRepository = appointmentAttendanceRepository;
        this.memberRepository = memberRepository;
    }


    /**
     * Returns all appointments with attendance status for a given user.
     *
     * @return the attendances of the members
     */
    @Override
    public List<MemberAttendanceDto> findAllGroupedByMembers() {
        return memberRepository.findAll().stream()
                .map(member -> {
                    List<AppointmentAttendance> appointmentAttendances =
                            appointmentAttendanceRepository.findByMemberUsername(member.getUsername());

                    return new MemberAttendanceDto(
                            member.getUsername(),
                            member.getName(),
                            member.getVoicePart(),
                            appointmentAttendances
                    );
                })
                .collect(Collectors.toList());
    }

}
