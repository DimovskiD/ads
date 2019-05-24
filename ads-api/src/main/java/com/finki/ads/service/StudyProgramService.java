package com.finki.ads.service;

import com.finki.ads.model.StudyProgram;

import java.util.List;

public interface StudyProgramService {

    List<StudyProgram> getStudyPrograms();
    StudyProgram getStudyProgramByName (String name);
    StudyProgram insert(String name);

    void delete(String id);
}
