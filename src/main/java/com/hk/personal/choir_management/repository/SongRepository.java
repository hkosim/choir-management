package com.hk.personal.choir_management.repository;

import com.hk.personal.choir_management.model.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
