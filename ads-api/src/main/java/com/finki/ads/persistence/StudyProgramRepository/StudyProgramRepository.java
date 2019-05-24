package com.finki.ads.persistence.StudyProgramRepository;


import com.finki.ads.model.StudyProgram;
import com.finki.ads.model.exceptions.StudentNotFoundException;
import com.finki.ads.model.exceptions.StudyProgramNotFoundException;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class StudyProgramRepository {

    private static List<StudyProgram> studyPrograms = new ArrayList<>();


    public List<StudyProgram> findAll() {
        return studyPrograms;
    }

    public StudyProgram getById(int id) {
        if (id >= studyPrograms.size()) {
            try {
                throw new StudentNotFoundException();
            } catch (StudentNotFoundException e) {
                return null;
            }
        }
        return studyPrograms.get(id);
    }

    public StudyProgram save(StudyProgram entity) {
        studyPrograms.add(entity);
        return studyPrograms.get(studyPrograms.size() - 1);

    }

    public StudyProgram deleteById(int id) {
        if (id >= studyPrograms.size() || id < 0) {
            try {
                throw new StudyProgramNotFoundException();
            } catch (StudyProgramNotFoundException e) {
                return null;
            }
        }
        return studyPrograms.remove(id);
    }

    public StudyProgram update(int index, StudyProgram studyProgram) {
        studyPrograms.set(index,studyProgram);
        return studyProgram;
    }

    public StudyProgram getByName(String name) {

        for (StudyProgram s:
                studyPrograms) {
            if (s.getName().equals(name)) return s;
        }
        return null;
    }

    public StudyProgram insert(String name) {
        StudyProgram sp = new StudyProgram();
        sp.setId(new Long(studyPrograms.size()));
        sp.setName(name);
        studyPrograms.add(sp);
        return sp;
    }
}
