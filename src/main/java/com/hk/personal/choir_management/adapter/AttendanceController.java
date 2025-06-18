package com.hk.personal.choir_management.adapter;

import com.hk.personal.choir_management.business.service.ChoirManagementBusinessService;
import com.hk.personal.choir_management.dto.attendance.MemberAttendanceDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final ChoirManagementBusinessService choirManagementBusinessService;

    public AttendanceController(ChoirManagementBusinessService choirManagementBusinessService) {
        this.choirManagementBusinessService = choirManagementBusinessService;
    }

    // GET ALL ATTENDANCE
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<MemberAttendanceDto> getAllAttendances() {
        return choirManagementBusinessService.getAllAttendances();
    }

}
