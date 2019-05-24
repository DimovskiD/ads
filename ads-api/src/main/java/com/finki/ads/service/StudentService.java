package com.finki.ads.service;

import com.finki.ads.model.Student;

import java.util.List;


public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentByIndex(String index);

    Student updateStudent(String index, Student student);

    Student deleteStudentByIndex(String id);

    Student saveStudent(Student entity);

    List<Student> getStudentsByStudyProgram(int id);

    void delete(String index);
}

