package com.hk.personal.choir_management.entity;

import com.hk.personal.choir_management.entity.superclass.Appointment;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "performances")
public class Performance extends Appointment {

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Song> songs;

    public Performance() {
    }

    public Performance(Long id, String title, String description, LocalDate date, LocalTime time, String location, List<Song> songs) {
        super(id, title, description, date, time, location);
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Performance{" +
                "songs=" + songs +
                '}';
    }
}
