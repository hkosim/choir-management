package com.hk.personal.choir_management.service;

import com.hk.personal.choir_management.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongService {
    Page<Song> findAll(Pageable pageable);

}
