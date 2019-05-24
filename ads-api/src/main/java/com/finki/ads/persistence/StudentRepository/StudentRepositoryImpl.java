package com.finki.ads.persistence.StudentRepository;

import com.finki.ads.model.Student;
import com.finki.ads.model.exceptions.StudentNotFoundException;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepositoryImpl  {

    private static List<Student> students = new ArrayList<>();


    public List<Student> findAll() {
        return students;
    }

    public Student getById(int id) {
        if (id >= students.size()) {
            try {
                throw new StudentNotFoundException();
            } catch (StudentNotFoundException e) {
                return new Student();
            }
        }
        return students.get(id);
    }

    public Student save(Student entity) {
        students.add(entity);
        return students.get(students.size() - 1);

    }

    public Student deleteByIndex(String index) {
        Student s = new Student();
        for (int i = 0; i<students.size(); i++) {
            if (students.get(i).getIndex().equals(index))
                s=students.remove(i);
        }
        return s;
    }


    public Student deleteById(int id) {
        if (id >= students.size() || id < 0) {
            try {
                throw new StudentNotFoundException();
            } catch (StudentNotFoundException e) {
                return null;
            }
        }
        return students.remove(id);
    }

    public Student update(String index, Student student) {
        for (int i = 0; i<students.size(); i++) {
            if (students.get(i).getIndex().equals(index))
                students.set(i,student);
        }
        return student;
    }

    public List<Student> getStudentByProgramId(int id) {
        List<Student> studentsList = new ArrayList<>();
        for (Student s :
                students) {
            if (s.getStudyProgram().getId() == id)
                studentsList.add(s);
        }
        return studentsList;
    }


    public Student getByIndex(String index) {
        for (Student s :
                students) {
            if (s.getIndex().equals(index))
                return s;
        }
        return null;
    }
}
