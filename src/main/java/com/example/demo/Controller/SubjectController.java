package com.example.demo.Controller;

import com.example.demo.Model.Shift;
import com.example.demo.Model.Student;
import com.example.demo.Model.Subject;
import com.example.demo.Repository.ShiftRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import java.util.List;
@RestController
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;
    @GetMapping("api/subject/list")
    public @ResponseBody ResponseEntity getShiftList(){
        List<Subject> subjectList = subjectRepository.findAll();
        return new ResponseEntity(subjectList, HttpStatus.OK);
    }

    @PostMapping("api/subject")
    public @ResponseBody ResponseEntity createSubject(@RequestBody Subject subject){
        if(subject.getName().trim().compareTo("") == 0){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        boolean isExists = subjectRepository.existsByName(subject.getName());
        if(isExists){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        subjectRepository.save(subject);
        return new ResponseEntity(HttpStatus.OK);
    }
}
