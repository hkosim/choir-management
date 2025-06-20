package com.hk.personal.choir_management.service.impl;

import com.hk.personal.choir_management.model.entity.Song;
import com.hk.personal.choir_management.repository.SongRepository;
import com.hk.personal.choir_management.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


    /**
     * Returns all paged songs.
     *
     * @param pageable the requested pagination configuration.
     * @return the songs with pagination.
     */
    @Override
    public Page<Song> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }
}
