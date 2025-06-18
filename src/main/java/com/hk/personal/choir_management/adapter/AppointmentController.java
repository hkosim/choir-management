package com.hk.personal.choir_management.adapter;

import com.hk.personal.choir_management.business.service.ChoirManagementBusinessService;
import com.hk.personal.choir_management.dto.AppointmentView;
import com.hk.personal.choir_management.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.dto.appointment.AppointmentAttendanceRequestDto;
import com.hk.personal.choir_management.entity.Rehearsal;
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
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<AppointmentAttendanceDto> getAppointments(
            @PageableDefault(sort = "id") Pageable pageable,
            @RequestParam(value = "username") String username
    ) {
        return choirManagementBusinessService.getAppointments(username, pageable);
    }

    /**
     * Returns an appointment given a type and id of the appointment.
     *
     * @param type the type of appointment.
     * @param id   the id of rehearsal or performance
     * @return a rehearsal oder performance.
     */
    @GetMapping(value = "appointment/{type}/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public AppointmentView getAppointment(
            @PathVariable String type,
            @PathVariable Long id
    ) {
        return choirManagementBusinessService.getAppointment(type, id);
    }

    /**
     * Updates a rehearsal
     *
     * @param rehearsal Rehearsal object
     * @return an updated rehearsal.
     */
    @PostMapping(value = "/rehearsal/save")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Rehearsal updateRehearsal(
            @RequestBody Rehearsal rehearsal
    ) {
        return choirManagementBusinessService.saveRehearsal(rehearsal);
    }

    /**
     * Saves an updated attendance.
     *
     * @param appointmentAttendanceDto the updated attendance informations.
     * @return the updated attendance.
     */
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public AppointmentAttendanceDto saveAttendance(
            @RequestBody AppointmentAttendanceRequestDto appointmentAttendanceDto
    ) {
        return choirManagementBusinessService.updateAppointment(appointmentAttendanceDto);
    }
}
