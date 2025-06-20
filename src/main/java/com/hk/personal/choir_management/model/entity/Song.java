package com.hk.personal.choir_management.model.entity;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String composer;
    private String musicalKey;
    private String voiceArrangement;
    private String source;

    public Song() {
    }

    public Song(Long id, String title, String composer, String musicalKey, String voiceArrangement, String source) {
        this.id = id;
        this.title = title;
        this.composer = composer;
        this.musicalKey = musicalKey;
        this.voiceArrangement = voiceArrangement;
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", composer='" + composer + '\'' +
                ", musicalKey='" + musicalKey + '\'' +
                ", voiceArrangement='" + voiceArrangement + '\'' +
                ", Source='" + source + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getMusicalKey() {
        return musicalKey;
    }

    public void setMusicalKey(String musicalKey) {
        this.musicalKey = musicalKey;
    }

    public String getVoiceArrangement() {
        return voiceArrangement;
    }

    public void setVoiceArrangement(String voiceArrangement) {
        this.voiceArrangement = voiceArrangement;
    }

}