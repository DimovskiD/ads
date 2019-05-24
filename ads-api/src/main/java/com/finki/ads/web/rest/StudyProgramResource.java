package com.finki.ads.web.rest;

import com.finki.ads.model.StudyProgram;
import com.finki.ads.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.util.List;


@RestController
@RequestMapping(value = "/study_programs", produces = MediaType.APPLICATION_JSON)
public class StudyProgramResource {

    private final StudyProgramService studyProgramService;

    @Autowired
    public StudyProgramResource(StudyProgramService studyProgramService) {
        this.studyProgramService = studyProgramService;
    }


    @GetMapping
    public List<StudyProgram> getStudyPrograms() {
        return studyProgramService.getStudyPrograms();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addNew(HttpEntity<String> name, HttpServletResponse response)  {

        StudyProgram studyProgram= studyProgramService.insert(name.getBody());
        return new ResponseEntity(studyProgram,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        studyProgramService.delete(id);
    }
}
