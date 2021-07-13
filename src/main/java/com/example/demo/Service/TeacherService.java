package com.example.demo.Service;

import com.example.demo.Model.Teacher;
import com.example.demo.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    public Optional<Teacher> findById(Integer teacherId){
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        optionalTeacher.isPresent();
        return optionalTeacher;
    }
}
