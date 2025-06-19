package com.hk.personal.choir_management.adapter;

import com.hk.personal.choir_management.business.service.ChoirManagementBusinessService;
import com.hk.personal.choir_management.dto.attendance.MemberAttendanceDto;
import com.hk.personal.choir_management.dto.member.*;
import com.hk.personal.choir_management.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final ChoirManagementBusinessService choirManagementBusinessService;

    public MemberController(ChoirManagementBusinessService choirManagementBusinessService) {
        this.choirManagementBusinessService = choirManagementBusinessService;
    }

    /**
     * Performs registration.
     *
     * @param registerRequest contains the data needed for registration.
     * @return response of the registration.
     */
    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return choirManagementBusinessService.register(registerRequest);
    }

    /**
     * Log in the user.
     *
     * @param loginRequest the login credentials.
     * @return the authentication result.
     */
    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest
    ) {
        return choirManagementBusinessService.login(loginRequest);
    }

    /**
     * Get roles
     *
     * @param username username from a Member.
     * @return the roles of the user.
     */
    @GetMapping(value = "/roles/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<String> getRoles(
            @PathVariable String username
    ) {
        return choirManagementBusinessService.getRoles(username);
    }

    /**
     * Returns a User given username or ID
     *
     * @param username as the ID of Member.
     * @return Profile of the Member.
     */
    @GetMapping(value = "/profile/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public MemberProfileDto getUserResultById(
            @PathVariable String username
    ) {
        return choirManagementBusinessService.getMemberProfile(username);
    }

    /**
     * Returns all the Members
     *
     * @return all Members.
     */
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Member> getAllMembers(
            @PageableDefault(sort = "username") Pageable pageable
    ) {
        return choirManagementBusinessService.getAllMembers(pageable);
    }

    /**
     * Returns all member with their respective attendances.
     *
     * @return the attendances of the members
     */
    @GetMapping(value = "/attendances")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<MemberAttendanceDto> getAllAttendances() {
        return choirManagementBusinessService.getAllAttendances();
    }

}
