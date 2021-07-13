package com.example.demo.Service;

import com.example.demo.Model.Subject;
import com.example.demo.Model.Teacher;
import com.example.demo.Repository.SubjectRepository;
import com.example.demo.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    public Optional<Subject> findById(Integer subjectId) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        return optionalSubject;
    }
}

