package com.finki.ads.web.rest;

import com.finki.ads.model.Student;
import com.finki.ads.model.StudentRequest;
import com.finki.ads.service.StudentService;
import com.finki.ads.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import java.util.List;

@RestController
@RequestMapping(value = "/students", produces = MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentService;
    private final StudyProgramService studyProgramService;

    @Autowired
    public StudentResource (StudentService studentService, StudyProgramService studyProgramService) {
        this.studentService = studentService;
        this.studyProgramService = studyProgramService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }


    @GetMapping("/{index}")
    public Student getStudentByIndex(@PathVariable("index") String index) {
        return studentService.getStudentByIndex(index);
    }

    @GetMapping("/by_study_program/{id}")
    public List<Student> getStudentsByStudyProgram(@PathVariable("id") int id)  {
        return studentService.getStudentsByStudyProgram(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addNew(@RequestBody StudentRequest student, HttpServletResponse response)  {
        if (student==null || student.getIndex()==null || student.getName() == null || student.getLastName() ==null || student.getStudyProgramName()==null || response == null) return new ResponseEntity("\"Missing parameters\"", HttpStatus.BAD_REQUEST);
        else if (!student.getIndex().matches("([0-9]{6})")) return new ResponseEntity("\"Malformatted index\"" , HttpStatus.BAD_REQUEST);
        else if (studyProgramService.getStudyProgramByName(student.getStudyProgramName())==null) return new ResponseEntity("\"No such program\"",HttpStatus.BAD_REQUEST);
        else if (studentService.getStudentByIndex(student.getIndex())!=null) return new ResponseEntity("\"Student with this index already exists\"",HttpStatus.BAD_REQUEST);
        Student s = new Student();
        s.setIndex(student.getIndex());
        s.setName(student.getName());
        s.setLastName(student.getLastName());
        s.setStudyProgram(studyProgramService.getStudyProgramByName(student.getStudyProgramName()));
        studentService.saveStudent(s);
        response.setHeader("Location","/students/" + s.getIndex());
        return new ResponseEntity(s, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{index}")
    public ResponseEntity patchStudent(@PathVariable String index, @RequestBody StudentRequest student, HttpServletResponse response) {
        if (studentService.getStudentByIndex(index)==null)
            return new ResponseEntity("\"No student with that index\"", HttpStatus.NOT_FOUND);
        String spName = student.getStudyProgramName();
        if (studyProgramService.getStudyProgramByName(spName)==null)
            return new ResponseEntity("\"No such program\"", HttpStatus.BAD_REQUEST);
        String name = student.getName();

        String lastName = student.getLastName();
        Student s = new Student();
        s.setIndex(index);
        s.setName(name);
        s.setLastName(lastName);
        s.setStudyProgram(studyProgramService.getStudyProgramByName(spName));
        studentService.updateStudent(index,s);
        response.setHeader("Location","/students/" + s.getIndex());
        return new ResponseEntity(s, HttpStatus.CREATED);    }

    @DeleteMapping("/{index}")
    public void delete(@PathVariable String index) {
        if (studentService.getStudentByIndex(index)==null) new ResponseEntity("\"No student exists with this index\"", HttpStatus.NOT_FOUND);
       else studentService.delete(index);
    }

}
