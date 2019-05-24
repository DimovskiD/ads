package com.finki.ads.service.impl;

import com.finki.ads.model.Student;
import com.finki.ads.persistence.StudentRepository.StudentRepositoryImpl;
import com.finki.ads.service.StudentService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class StudentServiceImpl implements StudentService {

    private final StudentRepositoryImpl repository;

    public StudentServiceImpl(StudentRepositoryImpl repository) {
        this.repository = repository;
    }




    @Override
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @Override
    public Student getStudentByIndex(String index) {
        return repository.getByIndex(index);
    }

    @Override
    public Student updateStudent(String index, Student student) {
        return repository.update(index,student);
    }

    @Override
    public Student deleteStudentByIndex(String index) {
        return repository.deleteByIndex(index);
    }

    @Override
    public Student saveStudent(Student entity) {
        return repository.save(entity);
    }

    @Override
    public List<Student> getStudentsByStudyProgram(int id) {
        return repository.getStudentByProgramId(id);
    }

    @Override
    public void delete(String index) {
        repository.deleteByIndex(index);
    }


    public void delete(int id) {
        repository.deleteById(id) ;
    }
}
