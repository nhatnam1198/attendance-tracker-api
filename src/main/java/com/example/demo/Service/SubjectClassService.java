package com.example.demo.Service;

import com.example.demo.DTO.SubjectClassDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.SubjectClassRepository;
import com.example.demo.Repository.SubjectRepository;
import com.example.demo.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectClassService {
    @Autowired
    SubjectClassRepository subjectClassRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SubjectService subjectService;
    @Autowired
    TeacherService teacherService;

    public SubjectClass getById(Integer id){
        return subjectClassRepository.getById(id);
    }

    public SubjectClass addSubjectClass(SubjectClassDTO subjectClassDTO){
        Teacher teacher = new Teacher();
        Subject subject = new Subject();
        Optional<Teacher> teacherOptional = teacherService.findById(subjectClassDTO.getTeacherId());
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

    public ArrayList<SubjectClass> getBySubjectId(Integer subjectId, String email) {
        ArrayList<SubjectClass> subjectClassArrayList = subjectClassRepository.getBySubjectIdAndTeacherEmail(subjectId, email);
        return subjectClassArrayList;
    }

    public List<SubjectClass> getByTeacherUserName(String userName) {
        Teacher teacher = teacherRepository.getByEmail(userName);
        ArrayList<SubjectClass> subjectClassArrayList = subjectClassRepository.getByTeacher(teacher);
        return subjectClassArrayList;
    }

    public List<SubjectClass> getByTeacherId(Integer teacherId) {
        return subjectClassRepository.getByTeacherId(teacherId);
    }
}
