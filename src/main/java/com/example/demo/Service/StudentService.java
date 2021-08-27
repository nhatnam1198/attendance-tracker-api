package com.example.demo.Service;

import com.example.demo.Model.Student;
import com.example.demo.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public Optional<Student> getStudentByEmail(String email){
        Student student = studentRepository.findByEmail(email);
        return Optional.of(student);
    }
}
