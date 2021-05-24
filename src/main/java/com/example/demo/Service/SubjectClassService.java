package com.example.demo.Service;

import com.example.demo.DTO.SubjectClassDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.SubjectClassRepository;
import com.example.demo.Repository.SubjectRepository;
import com.example.demo.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SubjectClassService {
    @Autowired
    SubjectClassRepository subjectClassRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SubjectRepository subjectRepository;
    public SubjectClass getById(Integer id){
        return subjectClassRepository.getById(id);
    }

    public SubjectClass addSubjectClass(SubjectClassDTO subjectClassDTO){
        Teacher teacher = new Teacher();
        Subject subject = new Subject();
        TeacherService teacherService = new TeacherService();
        Optional<Teacher> teacherOptional = teacherService.findById(subjectClassDTO.getTeacherId());
        SubjectService subjectService = new SubjectService();
        Optional<Subject> subjectOptional = subjectService.findById(subjectClassDTO.getSubjectId());
        if(teacherOptional.isPresent()){
            teacher = teacherOptional.get();
        }

        if(subjectOptional.isPresent()){
            subject = subjectOptional.get();
        }

        SubjectClass subjectClass = new SubjectClass();
        subjectClass.setTeacher(teacher);
        subjectClass.setSubject(subject);
        subjectClass.setName(subjectClassDTO.getName());
        subjectClass.setStartDateTime(subjectClassDTO.getStartDateTime());
        subjectClass.setEndDateTime(subjectClassDTO.getEndDateTime());
        subjectClass.setStatus(subjectClassDTO.getStatus());
        return subjectClassRepository.save(subjectClass);
    }
    public boolean existsByName(String subjectClassName){
        boolean isExists = subjectClassRepository.existsByName(subjectClassName);
        return isExists;
    }

    public ArrayList<SubjectClass> getBySubjectId(Integer subjectId, Integer teacherId) {
        ArrayList<SubjectClass> subjectClassArrayList = subjectClassRepository.getBySubjectIdAndTeacherId(subjectId, teacherId);
        return subjectClassArrayList;
    }
}
