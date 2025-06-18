package com.hk.personal.choir_management.adapter;

import com.hk.personal.choir_management.business.service.ChoirManagementBusinessService;
import com.hk.personal.choir_management.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final ChoirManagementBusinessService choirManagementBusinessService;

    public SongController(ChoirManagementBusinessService choirManagementBusinessService) {
        this.choirManagementBusinessService = choirManagementBusinessService;
    }

    // GET ALL SONGS
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<Song> getAllSongs(
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return choirManagementBusinessService.getSongs(pageable);
    }


}
