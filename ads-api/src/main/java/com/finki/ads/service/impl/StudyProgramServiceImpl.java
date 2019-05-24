package com.finki.ads.service.impl;

import com.finki.ads.model.StudyProgram;
import com.finki.ads.persistence.StudyProgramRepository.StudyProgramRepository;
import com.finki.ads.service.StudyProgramService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyProgramServiceImpl implements StudyProgramService {


    private final StudyProgramRepository repository;

    public StudyProgramServiceImpl(StudyProgramRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<StudyProgram> getStudyPrograms() {
        return repository.findAll();
    }

    @Override
    public StudyProgram getStudyProgramByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public StudyProgram insert(String name) {

        return repository.insert( name);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(Integer.parseInt(id));
    }
}
