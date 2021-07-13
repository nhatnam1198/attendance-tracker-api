package com.example.demo.Controller;

import com.example.demo.DTO.SubjectClassDTO;
import com.example.demo.Model.SubjectClass;
import com.example.demo.Service.SubjectClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SubjectClassController {
    @Autowired
    SubjectClassService subjectClassService;

    @PostMapping("api/subjectClass")
    public @ResponseBody ResponseEntity<SubjectClass> addSubjectClass(@RequestBody SubjectClassDTO subjectClassDto){
        if(subjectClassDto!= null && (subjectClassDto.getEndDateTime().equals("") || subjectClassDto.getName().equals("") || subjectClassDto.getStartDateTime().equals(""))){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(subjectClassService.existsByName(subjectClassDto.getName())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        SubjectClass createdResult = subjectClassService.addSubjectClass(subjectClassDto);
        return new ResponseEntity<SubjectClass>(createdResult, HttpStatus.CREATED);
    }

    @GetMapping("api/subjectClass/list")
    public @ResponseBody
    ResponseEntity<ArrayList<SubjectClass>> getBySubjectIdAndTeacherId(Integer subjectId, Integer teacherId){
        List<SubjectClass> subjectClassArrayList = subjectClassService.getBySubjectId(subjectId, teacherId);
        return new ResponseEntity(subjectClassArrayList, HttpStatus.OK);
    }

    @GetMapping("api/subjectClass/teacher/list")
    public @ResponseBody
    ResponseEntity<ArrayList<SubjectClass>> getByTeacherId(@RequestParam("teacherId") Integer teacherId){
        List<SubjectClass> subjectClassArrayList = subjectClassService.getByTeacherId(teacherId);
        return new ResponseEntity(subjectClassArrayList, HttpStatus.OK);
    }
}
