package com.hk.personal.choir_management.service.impl;

import com.hk.personal.choir_management.dto.attendance.AttendanceDto;
import com.hk.personal.choir_management.dto.attendance.MemberAttendanceDto;
import com.hk.personal.choir_management.repository.RehearsalAttendanceRepository;
import com.hk.personal.choir_management.repository.MemberRepository;
import com.hk.personal.choir_management.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final RehearsalAttendanceRepository rehearsalAttendanceRepository;
    private final MemberRepository memberRepository;

    public AttendanceServiceImpl(RehearsalAttendanceRepository rehearsalAttendanceRepository, MemberRepository memberRepository) {
        this.rehearsalAttendanceRepository = rehearsalAttendanceRepository;
        this.memberRepository = memberRepository;
    }

    // Returns all attendance grouped by members
    @Override
    public List<MemberAttendanceDto> findAllGroupedByMembers() {
        return memberRepository.findAll().stream()
                .map(member -> {
                    List<AttendanceDto> attendances =
                            rehearsalAttendanceRepository.findByMemberUsername(member.getUsername())
                                .stream()
                                .map(att -> new AttendanceDto(att.getRehearsal().getDate(), att.isPresent()))
                                .collect(Collectors.toList());

                    return new MemberAttendanceDto(
                            member.getUsername(),
                            member.getName(),
                            member.getVoicePart(),
                            attendances
                    );
                })
                .collect(Collectors.toList());
    }
}
