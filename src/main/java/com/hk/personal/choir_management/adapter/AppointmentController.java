package com.hk.personal.choir_management.adapter;

import com.hk.personal.choir_management.business.service.ChoirManagementBusinessService;
import com.hk.personal.choir_management.model.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.model.dto.appointment.AppointmentAttendanceRequestDto;
import com.hk.personal.choir_management.model.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final ChoirManagementBusinessService choirManagementBusinessService;

    public AppointmentController(ChoirManagementBusinessService choirManagementBusinessService) {
        this.choirManagementBusinessService = choirManagementBusinessService;
    }

    /**
     * Returns all appointments with attendance status for a given user.
     *
     * @param pageable the returned page settings
     * @param username the user's username
     * @return the attendances of the members
     */
    @GetMapping(value = "/attendances")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<AppointmentAttendanceDto> getMemberAttendances(
            @PageableDefault(sort = "id") Pageable pageable,
            @RequestParam(value = "username") String username
    ) {
        return choirManagementBusinessService.getMemberAttendances(username, pageable);
    }

    /**
     * Returns an appointment given the id of the appointment.
     *
     * @param id the id of appointment
     * @return an appointment
     */
    @GetMapping(value = "/appointment/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Appointment getAppointment(
            @PathVariable Long id
    ) {
        return choirManagementBusinessService.getAppointment(id);
    }

    /**
     * Updates or creates an appointment
     *
     * @param appointment Rehearsal object
     * @return a new or an updated appointment.
     */
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Appointment saveAppointment(
            @RequestBody Appointment appointment
    ) {
        return choirManagementBusinessService.saveAppointment(appointment);
    }


    /**
     * Saves an attendance; create a new one if already existed
     *
     * @param appointmentAttendanceDto the new attendance or updated attendance information.
     * @return the new or updated attendance.
     */
    @PostMapping(value = "/attendance/save")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public AppointmentAttendanceDto saveAttendance(
            @RequestBody AppointmentAttendanceRequestDto appointmentAttendanceDto
    ) {
        return choirManagementBusinessService.updateAttendance(appointmentAttendanceDto);
    }
}
