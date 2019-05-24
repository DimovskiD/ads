package com.finki.ads.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StudyProgram {

    @Id
    private Long id;
    private String name;

    public StudyProgram(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
