package com.hk.personal.choir_management.dto.appointment;

import com.hk.personal.choir_management.dto.AppointmentView;
import com.hk.personal.choir_management.entity.Song;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record PerformanceDto(
        Long id,
        String type,
        String title,
        String description,
        LocalDate date,
        LocalTime time,
        String location,
        List<Song> songs
) implements AppointmentView {

}
